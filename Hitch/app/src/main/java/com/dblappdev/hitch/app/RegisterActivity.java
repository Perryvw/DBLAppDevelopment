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
import android.widget.TextView;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

/**
 * Created by s128232 on 10-3-2015.
 */
public class RegisterActivity extends Activity {

    //Preferences
    private SharedPreferences prefs;
    //text edit fields
    private EditText nameEdit;
    private EditText ageEdit;

    //FB
    private UiLifecycleHelper uiHelper;

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

        //FB
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
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

    /*
    Facebook stuff below
    Perhaps change something, but this should work.
     */
    // Called when session changes
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    // When session is changed, this method is called from callback method
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        final TextView fb_name = (TextView) findViewById(R.id.fb_name);
        final TextView fb_age = (TextView) findViewById(R.id.fb_age);
        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {
            Log.i("Facebook: ", "Logged in...");
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        // Set User name
                        fb_name.setText("Hello " + user.getName());
                        fb_age.setText("Age: " + user.getBirthday());
                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i("Facebook: ", "Logged out...");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i("Facebook: ", "OnActivityResult...");
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
}
