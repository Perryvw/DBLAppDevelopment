package com.dblappdev.hitch.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.dblappdev.hitch.app.GMapV2Direction;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import org.w3c.dom.Document;

import java.util.ArrayList;


public class RouteActivity extends FragmentActivity implements OnMapReadyCallback{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        onMapReady(mapFragment.getMap());
    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Testing if I can do stuff with it :D"));

        map.addCircle(new CircleOptions()
                .center(new LatLng(0, 0))
                .radius(5000000).fillColor(Color.BLUE));
//        LatLng fromPosition = new LatLng(13.687140112679154, 100.53525868803263);
//        LatLng toPosition = new LatLng(13.683660045847258, 100.53900808095932);
//
//        GMapV2Direction md = new GMapV2Direction();
//
//        Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
//
//        Log.d("getting doc onMapReady()", doc.toString());
//
//        ArrayList<LatLng> directionPoint = md.getDirection(doc);
//        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
//
//        for(int i = 0 ; i < directionPoint.size() ; i++) {
//            rectLine.add(directionPoint.get(i));
//        }
//
//        map.addPolyline(rectLine);

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
}
