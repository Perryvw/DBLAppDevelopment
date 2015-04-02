package com.dblappdev.hitch.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by s128232 on 10-3-2015.
 */
public class HitchRouteFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    // Variables for EditText fields
    private EditText startPoint, endPoint;
    private TextView currentLocation;
    private Switch currentLocationSwitch;

    // Variable for storing current date and time
    //private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hitch_route, container, false);

        getItems(rootView);

        currentLocationSwitch.setOnCheckedChangeListener(this);
        return rootView;
    }

    // get the selected dropdown list value
    public void getItems(View rootView) {
        startPoint = (EditText) rootView.findViewById(R.id.startPoint);
        endPoint = (EditText) rootView.findViewById(R.id.endPoint);
        currentLocation = (TextView) rootView.findViewById(R.id.currentLocation);
        currentLocationSwitch = (Switch) rootView.findViewById(R.id.currentLocationSwitch);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startPoint.setVisibility(View.INVISIBLE);
        } else {
            startPoint.setVisibility(View.VISIBLE);
        }
    }
}
