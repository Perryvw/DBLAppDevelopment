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
import com.dblappdev.hitch.model.User;
import com.dblappdev.hitch.network.API;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class RegisterActivity extends Activity {

    //Preferences
    private SharedPreferences prefs;
    //text edit fields
    private EditText nameEdit;
    private EditText ageEdit;
    private EditText phoneEdit;

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
        phoneEdit = (EditText) findViewById(R.id.userPhone);

        registerButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When the user presses register.
             */
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                int age = Integer.parseInt(ageEdit.getText().toString());
                String phone = phoneEdit.getText().toString();
                registerUser(name, age, phone);
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
     */
    private void registerUser(String name, int age, String phone) {
        int state = prefs.getInt(MainActivity.STATE_KEY, 0);

        User.registerUser(name, state, age, phone, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                registerCallback();
                return null;
            }
        });

    }

    public void registerCallback() {
        int id = -1;
        JSONObject json = API.getResponse();
        try {
            id = json.getInt("userID");
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (id == -1) {
            return;
        }
        //Log.e("callback register", Integer.toString(id));
        // update shared preferences, birthed and user
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MainActivity.USER_KEY, id);
        editor.commit();
        // navigate to main
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void finalizeRegister() {

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
                        nameEdit.setText(user.getName());
                        if (user.getBirthday() != null) {
                            ageEdit.setText(user.getBirthday());
                        }
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
