package com.dblappdev.hitch.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by s128232 on 10-3-2015.
 */
public class BirthActivity extends Activity {

    //Preferences
    private SharedPreferences prefs;
    //Dialog counter
    private int mStackLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        mStackLevel = 0;

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
     * Login by phone number.
     */
    public boolean loginUser(View view) {
        mStackLevel++;
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        LoginDialogFragment newFragment = LoginDialogFragment.newInstance(mStackLevel, this);
        newFragment.show(ft, "dialog");
        return false;
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

    public void startRootActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
