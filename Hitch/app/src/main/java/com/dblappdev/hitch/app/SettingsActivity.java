package com.dblappdev.hitch.app;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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

                // Show Alert
                switch (position) {
                    case 0:
                        //Hiker or driver
                        setState(view);
                        break;
                    case 1:
                        Intent editProfileActivity = new Intent(getApplicationContext(),com.dblappdev.hitch.app.EditProfileActivity.class);
                        startActivity(editProfileActivity);
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

    public void setState(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Choose if you want to be a hiker or driver");
        alertDialogBuilder.setPositiveButton("Driver",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Clicked driver!
                        Toast.makeText(getApplicationContext(),
                                "SetPreference: Driver" , Toast.LENGTH_LONG)
                                .show();

                    }
                });
        alertDialogBuilder.setNegativeButton("Hiker",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Clicked hiker!
                        Toast.makeText(getApplicationContext(),
                                "SetPreference: Hiker" , Toast.LENGTH_LONG)
                                .show();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
