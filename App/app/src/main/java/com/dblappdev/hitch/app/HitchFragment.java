package com.dblappdev.hitch.app;

/**
 * Created by s138308 on 24/02/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HitchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hitch, container, false);
        Activity act = this.getActivity();
        final Button button = (Button)  act.findViewById(R.id.toHitchActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), SettingsActivity.class);
                myIntent.putExtra("key", 3); //Optional parameters
                getActivity().startActivity(myIntent);
            }
        });

        return rootView;
    }

}