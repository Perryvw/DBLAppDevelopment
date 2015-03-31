package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import com.dblappdev.hitch.model.User;

import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class ViewProfileActivity extends Activity {

    private User user;
    private TextView nameView, birthdateView, registeredView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int userID = bundle.getInt("userID");

        setContentView(R.layout.activity_viewprofile);

        user = new User(userID, true, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });

        nameView = (TextView) findViewById(R.id.nameLabel);
        birthdateView = (TextView) findViewById(R.id.birthdateLabel);
        registeredView = (TextView) findViewById(R.id.registeredLabel);
    }

    public void callback() {
        nameView.setText(user.getName());
        birthdateView.setText(user.getBirthdate());
        registeredView.setText(user.getJoinedDate());
    }
}