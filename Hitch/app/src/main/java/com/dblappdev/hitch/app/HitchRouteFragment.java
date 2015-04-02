package com.dblappdev.hitch.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.dblappdev.hitch.network.API;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by s128232 on 10-3-2015.
 */
public class HitchRouteFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    // Variables for EditText fields
    private EditText startPoint, endPoint;
    private Switch currentLocationSwitch;
    private Button submitButton;
    private SharedPreferences prefs;
    private Double lng, lat;

    // Variable for storing current date and time
    //private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hitch_route, container, false);

        prefs = getActivity().getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        getItems(rootView);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        currentLocationSwitch.setOnCheckedChangeListener(this);
        submitButton.setOnClickListener(this);
        return rootView;
    }

    // get the selected dropdown list value
    public void getItems(View rootView) {
        startPoint = (EditText) rootView.findViewById(R.id.startPoint);
        endPoint = (EditText) rootView.findViewById(R.id.endPoint);
        currentLocationSwitch = (Switch) rootView.findViewById(R.id.currentLocationSwitch);
        submitButton = (Button) rootView.findViewById(R.id.button8);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {

            startPoint.setText(lng + " " + lat);
        } else {
            startPoint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int userID = prefs.getInt(MainActivity.USER_KEY, -1);
        if (userID == -1) {
            return;
        }
        String location = startPoint.getText().toString();
        String destination = endPoint.getText().toString();
        long unixTime = System.currentTimeMillis() / 1000L;

        new API().addUserHitchhikeData(userID, location, destination, unixTime);

        android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.hitch_fragment, new HitchFragment());
        ft.addToBackStack(null);
        ft.commit();
        HitchFragment.LOAD = true;
    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            lng = loc.getLongitude();
            lat = loc.getLatitude();
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
