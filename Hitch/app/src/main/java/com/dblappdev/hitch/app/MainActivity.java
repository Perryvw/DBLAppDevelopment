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
    public static final String STATE_KEY = "com.dblappdev.hitch.state";

    private SharedPreferences prefs;

    /**
     * When the app is giving birth the intent BirthActivity will be started. Otherwise we will start TabViewActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        //undoBirth();
        boolean hadBirth = prefs.getBoolean(BIRTH_KEY, false);
        if (! hadBirth) {
            Intent intent = new Intent(this, BirthActivity.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(this, TabViewActivity.class);
        startActivity(intent);
    }

    private void undoBirth() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(BIRTH_KEY, false);
        editor.commit();
    }
}
