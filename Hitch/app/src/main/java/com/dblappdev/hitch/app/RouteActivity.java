package com.dblappdev.hitch.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.dblappdev.hitch.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class RouteActivity extends FragmentActivity implements OnMapReadyCallback{

    ArrayList<LatLng> directions;
    MapFragment mapFragment;
    private User user;
    private TextView nameView, birthdateView, registeredView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        directions = (ArrayList) bundle.get("LATLNG_DIRECTIONS_LIST");
        onMapReady(mapFragment.getMap());
        setCameraOnRoute(directions);

        //THIS SHOULD BE THE USERID OF THE DRIVER INSTEAD
        int userID = 1;

        nameView = (TextView) findViewById(R.id.nameLabel);
        birthdateView = (TextView) findViewById(R.id.birthdateLabel);
        registeredView = (TextView) findViewById(R.id.registeredLabel);

        user = new User(userID, true, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
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

        final LatLngBounds bounds = new LatLngBounds.Builder().include(new LatLng(maxLat, maxLon)).include(new LatLng(minLat, minLon)).build();

        mapFragment.getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapFragment.getMap().setPadding(10, 10, 10, 10);
                mapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {

        PolylineOptions rectLine = new PolylineOptions().width(6).color(Color.GRAY);

        for(int i = 0 ; i < directions.size() ; i++) {
            rectLine.add(directions.get(i));
        }

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

    public void callback() {
        nameView.setText(user.getName());
        birthdateView.setText(user.getBirthdate());
        registeredView.setText(user.getJoinedDate());
    }
}
