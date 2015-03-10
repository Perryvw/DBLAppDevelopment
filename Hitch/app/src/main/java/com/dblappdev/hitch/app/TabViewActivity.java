package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by guusleijsten on 10/03/15.
 */
public class TabViewActivity extends Activity {

    /**
     * When the shared variable DRIVER_MODE_KEY is true, we will initialize the tabbed view with DriverFragment,
     * otherwise with HitchFragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        boolean driverMode = prefs.getBoolean(MainActivity.DRIVER_MODE_KEY, false);
        if (driverMode) { //driver
            //TODO: show driver fragment
        } else { //hiker
            //TODO: show hiker fragment
        }


        setContentView(R.layout.activity_main);
    }
}
