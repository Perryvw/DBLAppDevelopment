package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    //Preferences
    private SharedPreferences prefs;
    //text edit fields
    private EditText nameEdit;
    private EditText ageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_register);

        Button registerButton = (Button) findViewById(R.id.btn_register);
        nameEdit = (EditText) findViewById(R.id.userName);
        ageEdit = (EditText) findViewById(R.id.userAge);

        registerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the user presses register.
             */
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                int age = Integer.parseInt(ageEdit.getText().toString());
                int state = getState();
                registerUser(name, age, state);
            }
        });
    }

    /**
     * Register a user.
     *
     * @param name the name of the user
     * @param age the age of the user
     * @param state 0 for driver, 1 for hitcher
     */
    private void registerUser(String name, int age, int state) {
        Log.d("registerUser", "{name: \"" + name + "\", age: " + age + ", state: " + state + "}");
        giveBirth();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        /**


        //setup request
        String getRequest = "func=RegisterUser&name="+name+"&state="+state+"&hitchhikes=0&avatarURL=";

        try {
            JSONObject response = HttpURLConnection.sendGet(getRequest);
            Log.d("response", response.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
         */
    }

    private int getState() {
        return prefs.getInt(MainActivity.STATE_KEY, 0);
    }

    /**
     * Sets the shared preference boolean birth to true.
     */
    private void giveBirth() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(MainActivity.BIRTH_KEY, true);
        editor.commit();
    }
}
