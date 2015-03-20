package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    public static final String SHARED_PREF = "com.dblappdev.hitch";
    public static final String STATE_KEY = "com.dblappdev.hitch.mode";
    public static final String USER_KEY = "com.dblappdev.hitch.user";

    /**
     * When the app is giving birth the intent BirthActivity will be started. Otherwise we will start TabViewActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        int userID = prefs.getInt(USER_KEY, -1);

        if (userID == -1) {
            Intent intent = new Intent(this, BirthActivity.class);
            startActivity(intent);
        } else {
            Log.d("userID", Integer.toString(userID));
            Intent intent = new Intent(this, TabViewActivity.class);
            startActivity(intent);
        }
    }
}