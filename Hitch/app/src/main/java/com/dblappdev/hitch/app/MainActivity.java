package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    public static final String SHARED_PREF = "com.dblappdev.hitch";
    public static final String BIRTH_KEY = "com.dblappdev.hitch.birth";
    public static final String STATE_KEY = "com.dblappdev.hitch.mode";

    /**
     * When the app is giving birth the intent BirthActivity will be started. Otherwise we will start TabViewActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        boolean birthControl = prefs.getBoolean(BIRTH_KEY, false);
        if (! birthControl) {
            Intent intent = new Intent(this, BirthActivity.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(this, TabViewActivity.class);
        startActivity(intent);
    }

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
*/
}