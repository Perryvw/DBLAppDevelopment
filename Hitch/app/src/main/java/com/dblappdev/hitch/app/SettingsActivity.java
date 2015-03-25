package com.dblappdev.hitch.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by s128232 on 10-3-2015.
 */
public class SettingsActivity extends FragmentActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Hiker or driver",
                "User profile",
                "Saved routes",
                "Advanced settings"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                int  itemValue = position;

                // Show Alert
                switch (itemValue) {
                    case 0:
                        //Hiker or driver
                        Toast.makeText(getApplicationContext(),
                                "Hiker or driver, open pop-up" , Toast.LENGTH_LONG)
                                .show();
                        break;
                    case 1:
                        //Profile
                        Toast.makeText(getApplicationContext(),
                                "Profile, start edit profile intent" , Toast.LENGTH_LONG)
                                .show();
                        break;
                    case 2:
                        //Saved routes
                        Toast.makeText(getApplicationContext(),
                                "Saved routes, new intent?" , Toast.LENGTH_LONG)
                                .show();
                        break;
                    case 3:
                        //Advanced settings
                        Toast.makeText(getApplicationContext(),
                                "Cache and stuff?" , Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            }

        });
    }
}
