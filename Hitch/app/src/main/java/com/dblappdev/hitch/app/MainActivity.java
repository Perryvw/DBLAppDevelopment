package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.dblappdev.hitch.model.User;
import com.dblappdev.hitch.route.RouteDisplayer;

import java.util.concurrent.Callable;


public class MainActivity extends Activity {

    /** Shared preferences. */
    public static final String SHARED_PREF = "com.dblappdev.hitch";
    /** User state: 0 for driver, 1 for hitcher. */
    public static final String STATE_KEY = "com.dblappdev.hitch.mode";
    /** userID: -1 for not set, >= 0 otherwise. */
    public static final String USER_KEY = "com.dblappdev.hitch.user";
    
    /**
     * When the app is giving birth the intent BirthActivity will be started. Otherwise we will start TabViewActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences prefs = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        Intent intent;

        int userID = prefs.getInt(USER_KEY, -1);
        if (userID == -1) {
            intent = new Intent(this, BirthActivity.class);
        } else {
            //Log.d("userID", Integer.toString(userID));
            intent = new Intent(this, TabViewActivity.class);
        }

        //RouteDisplayer.getInstance().showRouteActivity("Maastricht","Leeuwarden",1,"11:00","15:00",getApplicationContext());
        //intent = new Intent(this, RouteActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}