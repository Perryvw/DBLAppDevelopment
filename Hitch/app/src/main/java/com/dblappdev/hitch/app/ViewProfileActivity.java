package com.dblappdev.hitch.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import com.dblappdev.hitch.adapter.RatingAdapter;
import com.dblappdev.hitch.model.ListViewItem;
import com.dblappdev.hitch.model.RatingViewItem;
import com.dblappdev.hitch.model.User;
import com.dblappdev.hitch.network.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class ViewProfileActivity extends ListActivity {

    private User user;
    private TextView nameView, birthdateView, registeredView;
    private ArrayList<RatingViewItem> ratingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_viewprofile);

        int userID = bundle.getInt("userID");
        if (userID == -1) {
            return;
        }

        nameView = (TextView) findViewById(R.id.nameLabel);
        birthdateView = (TextView) findViewById(R.id.birthdateLabel);
        registeredView = (TextView) findViewById(R.id.registeredLabel);

        user = new User(userID, true, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });

        ratingList = new ArrayList<RatingViewItem>();
        final API api = new API();
        api.getUserRatings(userID, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                userRatingsCallback(api);
                return null;
            }
        });
    }

    public void callback() {
        nameView.setText(user.getName());
        birthdateView.setText(user.getBirthdate());
        registeredView.setText(user.getJoinedDate());
    }

    public void userRatingsCallback(API api) {
        JSONObject json = api.getResponse();
        try {
            JSONArray arr = json.getJSONArray("ratings");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject rating = arr.getJSONObject(i);
                String userName = rating.getString("userName");
                String comment = rating.getString("comment");
                int stars = rating.getInt("rating");

                ratingList.add(new RatingViewItem(userName, comment, stars));
            }

            RatingAdapter ratingAdapter = new RatingAdapter(this, ratingList);

            setListAdapter(ratingAdapter);


        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}