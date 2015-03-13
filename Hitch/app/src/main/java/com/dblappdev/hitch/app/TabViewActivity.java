package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
