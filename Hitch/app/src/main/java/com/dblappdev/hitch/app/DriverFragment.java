package com.dblappdev.hitch.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dblappdev.hitch.network.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class DriverFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        int userID = prefs.getInt(MainActivity.USER_KEY, -1);

        View rootView = inflater.inflate(R.layout.fragment_driver, container, false);


        API.getUserRoutes(userID, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });

        return rootView;
    }

    public void callback() {
        JSONObject json = API.getResponse();
        try {
            JSONArray arr = json.getJSONArray("routes");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject route = arr.getJSONObject(i);
                int routeID = route.getInt("routeID");
                int userID = route.getInt("userID");
                String startPoint = route.getString("startPoint");
                String endPoint = route.getString("endPoint");
                String timestamp = route.getString("timestamp");
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
