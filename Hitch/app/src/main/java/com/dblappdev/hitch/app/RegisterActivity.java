package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONObject;

/**
 * Created by s128232 on 10-3-2015.
 */
public class RegisterActivity extends Activity {

    private EditText nameEdit;
    private EditText ageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = (Button) findViewById(R.id.btn_register);
        nameEdit = (EditText) findViewById(R.id.userName);
        ageEdit = (EditText) findViewById(R.id.userAge);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                int age = Integer.parseInt(ageEdit.getText().toString());
                registerUser(name, age);
            }
        });
    }

    private void registerUser(String name, int age) {
        //get state
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        boolean driverMode = prefs.getBoolean(MainActivity.DRIVER_MODE_KEY, false);
        String state = prefs.getBoolean(MainActivity.DRIVER_MODE_KEY, false)? "0": "1";

        //setup request
        String getRequest = "func=RegisterUser&name="+name+"&state="+state+"&hitchhikes=0&avatarURL=";

        try {
            JSONObject response = HttpURLConnection.sendGet(getRequest);
            Log.d("response", response.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void giveBirth() {
        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(MainActivity.BIRTH_KEY, true);
    }
}
