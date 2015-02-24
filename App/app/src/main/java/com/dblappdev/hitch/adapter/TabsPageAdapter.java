package com.dblappdev.hitch.adapter;

/**
 * Created by s138308 on 24/02/2015.
 */
import com.dblappdev.hitch.app.HitchFragment;
import com.dblappdev.hitch.app.DriverFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPageAdapter extends FragmentPagerAdapter {

    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new DriverFragment();
            case 1:
                // Games fragment activity
                return new HitchFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}