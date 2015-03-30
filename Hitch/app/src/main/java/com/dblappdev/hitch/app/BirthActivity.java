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
        startActivity(intent);
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
        Intent intent;
        switch(id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.action_profileEdit:
                intent = new Intent(this, EditProfileActivity.class);
                break;
            case R.id.action_profileView:
                intent = new Intent(this, ViewProfileActivity.class);
                break;
            case R.id.action_openChat:
                intent = new Intent(this, ChatActivity.class);
                break;
            case R.id.action_routeActivity:
                intent = new Intent(this, RouteActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }
}
