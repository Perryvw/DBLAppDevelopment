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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birth);
    }

    /**
     * Starts Register activity.
     */
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Updates the shared preference to indicate that hitch has given birth and sets the DRIVER_MODE to false.
     */
    public void giveBirthHiker(View view) {
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(MainActivity.DRIVER_MODE_KEY, false);
        register();
    }

    /**
     * Updates the shared preference to indicate that hitch has given birth and sets the DRIVER_MODE to true.
     */
    public void giveBirthDriver(View view) {
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(MainActivity.DRIVER_MODE_KEY, true);
        register();
    }
}
