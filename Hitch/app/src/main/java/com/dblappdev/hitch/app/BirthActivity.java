package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * Created by s128232 on 10-3-2015.
 */
public class BirthActivity extends Activity {

    //Preferences
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_birth);
    }

    /**
     * Sets the shared preference boolean driver mode.
     *
     * @param state 0 for driver, 1 for hiker
     */
    private void setState(int state) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MainActivity.STATE_KEY, state);
        editor.commit();
    }

    /**
     * Go to register activity as a hiker.
     */
    public void startAsHiker(View view) {
        setState(1);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Go to register activity as a driver.
     */
    public void startAsDriver(View view) {
        setState(0);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
