package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import com.dblappdev.hitch.model.User;

import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class EditProfileActivity extends Activity {

    private User user;
    private EditText nameView, birthdateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        int userID = prefs.getInt(MainActivity.USER_KEY, -1);

        setContentView(R.layout.activity_editprofile);

        user = new User(userID, true, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });

        nameView = (EditText) findViewById(R.id.editName);
        birthdateView = (EditText) findViewById(R.id.editDate);
    }

    public void callback() {
        nameView.setText(user.getName());
        birthdateView.setText(user.getBirthdate());
    }
}
