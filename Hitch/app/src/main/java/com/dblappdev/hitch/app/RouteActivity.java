package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.dblappdev.hitch.model.User;
import com.dblappdev.hitch.network.API;
import com.dblappdev.hitch.route.GMapV2Direction;
import com.dblappdev.hitch.route.GetRouteDirectionsTask;
import com.dblappdev.hitch.route.Route;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;


public class RouteActivity extends FragmentActivity implements OnMapReadyCallback{

    ArrayList<LatLng> directions;
    MapFragment mapFragment;
    Route route;
    private User user;
    private TextView nameView, birthdateView, registeredView, routeNameView, depTimeView, etaView;
    private Button endHitchButton,openChatButton;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        route = (Route) bundle.get("ROUTE");
        String url = (new GMapV2Direction()).getDocDrivingUrl(route);
        //String url = (new GMapV2Direction()).getDocWalkingUrl(route);
        new GetRouteDirectionsTask(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                buildRouteActivity();
                return null;
            }
        }, route).execute(url);


    }

    private void buildRouteActivity() {
        loadComponents();
        directions = route.getDirections();
        onMapReady(mapFragment.getMap());
        setCameraOnRoute(directions);
        initEndHitchButton();
        initStartChatButton();
    }

    private void initEndHitchButton() {
        endHitchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    endHitchButton.setBackgroundColor(darker(Color.RED, 0.7f));


                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    endHitchButton.setBackgroundColor(Color.RED);

                    //TODO END HITCH BUTTON FUNCTIONALITY HERE
                }

                return false;
            }
        });
    }

    private void loadComponents() {

        //THIS SHOULD BE THE USERID OF THE DRIVER INSTEAD
        int userID = route.getOwnerID();

        nameView = (TextView) findViewById(R.id.nameLabel);
        birthdateView = (TextView) findViewById(R.id.birthdateLabel);
        registeredView = (TextView) findViewById(R.id.registeredLabel);
        routeNameView = (TextView) findViewById(R.id.routeName);
        depTimeView = (TextView) findViewById(R.id.departureTime);
        etaView = (TextView) findViewById(R.id.arrivalTime);
        openChatButton = (Button) findViewById(R.id.openChatButton);
        endHitchButton = (Button) findViewById(R.id.endHitchButton);

        user = new User(userID, true, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });
    }

    private void initStartChatButton() {
        final Activity thisAct = this;
        openChatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                    openChatButton.setBackgroundColor(darker(Color.BLUE,0.7f));




                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    openChatButton.setBackgroundColor(Color.BLUE);

                    //TODO OPEN CHAT BUTTON FUNCTIONALITY HERE
                    int userID = prefs.getInt(MainActivity.USER_KEY, -1);
                    final API api = new API();
                    api.createChatbox(userID, route.getOwnerID(), new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            //get the chat id from the response
                            JSONObject response = api.getResponse();
                            int chatID = response.getInt("chatID");

                            Intent intent = new Intent(thisAct, ChatActivity.class);
                            intent.putExtra("CHAT_ID", chatID);
                            startActivity(intent);
                            return null;
                        }
                    });
                }

                return false;
            }
        });
    }

    private void setCameraOnRoute(ArrayList<LatLng> directions) {

        double minLat = Integer.MAX_VALUE;
        double maxLat = Integer.MIN_VALUE;
        double minLon = Integer.MAX_VALUE;
        double maxLon = Integer.MIN_VALUE;

        for(int i = 0 ; i < directions.size() ; i++) {
            maxLat = Math.max(directions.get(i).latitude, maxLat);
            minLat = Math.min(directions.get(i).latitude, minLat);
            maxLon = Math.max(directions.get(i).longitude, maxLon);
            minLon = Math.min(directions.get(i).longitude, minLon);
        }

        final LatLngBounds bounds = new LatLngBounds.Builder().include(new LatLng(maxLat, maxLon))
                .include(new LatLng(minLat, minLon)).build();

        mapFragment.getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapFragment.getMap().setPadding(10, 10, 10, 10);
                mapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
            }
        });

        mapFragment.getMap().getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        PolylineOptions rectLine = new PolylineOptions().width(6).color(Color.rgb(225,102,0));

        for(int i = 0 ; i < directions.size() ; i++) {
            rectLine.add(directions.get(i));
        }

        Marker start = mapFragment.getMap().addMarker(new MarkerOptions()
                .position(new LatLng(route.getStart().getLatitude(), route.getStart().getLongitude()))
                .title(route.getStart().getCity())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .snippet("Start point route")
                .alpha(0.7f));

        Marker end = mapFragment.getMap().addMarker(new MarkerOptions()
                .position(new LatLng(route.getEnd().getLatitude(), route.getEnd().getLongitude()))
                .title(route.getEnd().getCity())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .snippet("End point route")
                .alpha(0.7f));

        start.isVisible();

        end.isVisible();

        map.addPolyline(rectLine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Returns darker version of specified <code>color</code>.
     */
    public static int darker (int color, float factor) {
        int a = Color.alpha( color );
        int r = Color.red( color );
        int g = Color.green( color );
        int b = Color.blue( color );

        return Color.argb( a,
                Math.max( (int)(r * factor), 0 ),
                Math.max( (int)(g * factor), 0 ),
                Math.max( (int)(b * factor), 0 ) );
    }

    private void callback() {
        nameView.setText(user.getName());
        birthdateView.setText(user.getBirthdate());
        registeredView.setText(user.getJoinedDate());
        routeNameView.setText(route.getStart().getCity() + " - " + route.getEnd().getCity());
        depTimeView.setText(route.getDepTime());
        etaView.setText(route.getEta());
    }
}
