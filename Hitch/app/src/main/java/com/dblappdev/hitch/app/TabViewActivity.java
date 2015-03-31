package com.dblappdev.hitch.app;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.dblappdev.hitch.adapter.TabsPageAdapter;
import com.dblappdev.hitch.model.User;

/**
 * Created by guusleijsten on 10/03/15.
 */
public class TabViewActivity extends FragmentActivity implements TabListener {

    private SharedPreferences prefs;

    private ViewPager viewPager;
    private TabsPageAdapter mAdapter;
    private ActionBar actionBar;

    /**
     * When the shared variable DRIVER_MODE_KEY is true, we will initialize the tabbed view with DriverFragment,
     * otherwise with HitchFragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        int state = prefs.getInt(MainActivity.STATE_KEY, 0);

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        Resources res = getResources();
        String[] tabs = new String[2];
        tabs[0] = res.getString(R.string.driver);
        tabs[1] = res.getString(R.string.hiker);
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        //Set viewPager to the correct screen
        int item = viewPager.getCurrentItem();
        if (item != state) {
            viewPager.setCurrentItem(state);
            actionBar.setSelectedNavigationItem(state);
        }

        //Add page change listener after setting the correct page (above)
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
                setState(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) { }

            @Override
            public void onPageScrollStateChanged(int arg0) { }
        });
    }

    public void setState(int newState) {
        //Log.e("state change", Integer.toString(newState));
        // apply to database
        User user = new User(prefs.getInt(MainActivity.USER_KEY, -1), false, null);
        user.setState(newState);
        // update respected shared preference
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MainActivity.STATE_KEY, newState);
        editor.commit();
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
            case R.id.action_signout:
                resetUser();
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                break;
            case R.id.action_profileView:
                int userID = prefs.getInt(MainActivity.USER_KEY, -1);
                if (userID == -1) {
                    return false;
                }
                intent = new Intent(this, ViewProfileActivity.class);
                intent.putExtra("userID", prefs.getInt(MainActivity.USER_KEY, -1));
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

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    /**
     * Sets the shared preference containing user id to -1, it's initial value.
     */
    private void resetUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MainActivity.USER_KEY, -1);
        editor.commit();
    }

    /**
     * Go to add a route for configuring driver routes.
     */
    public void addHitchRoute(View v) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.hitch_fragment, new HitchRouteFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Go to add a route for configuring driver routes.
     */
    public void addDriverRoute(View v) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.driver_fragment, new DriverRouteFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Go to driver home.
     */
    public void goDriverHome(View v) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_driver_route, new DriverFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
