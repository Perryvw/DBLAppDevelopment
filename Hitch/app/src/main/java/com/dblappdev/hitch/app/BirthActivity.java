package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by s128232 on 10-3-2015.
 */
public class BirthActivity extends Activity {

    /**
     * When the app is giving birth the intent BirthActivity will be started.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_birth);
    }

    /**
     * Updates the shared preference to indicate that hitch has given birth.
     */
    public void giveBirth() {
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(MainActivity.BIRTH_KEY, true);
    }
}
