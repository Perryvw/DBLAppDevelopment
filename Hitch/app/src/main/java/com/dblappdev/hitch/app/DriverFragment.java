package com.dblappdev.hitch.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import com.dblappdev.hitch.adapter.ExpandableListAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.dblappdev.hitch.app.R;
import com.dblappdev.hitch.model.User;
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
    // Route dropdown list
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> routeCollection;
    private SharedPreferences prefs;
    private int userID;
    ExpandableListAdapter expListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_driver, container, false);

        prefs = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        createGroupList();

        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.route_list);
        expListAdapter = new ExpandableListAdapter(this, groupList, routeCollection);
        expListView.setAdapter(expListAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getActivity().getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                userID = prefs.getInt(MainActivity.USER_KEY, -1);
                return true;
            }
        });

        return rootView;
    }


    private void createGroupList() {
        routeCollection = new LinkedHashMap<String, List<String>>();
        groupList = new ArrayList<String>();
        final API api = new API();

        api.getUserRoutes(2, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                createGroupListCallback(api);
                for (String routeName: groupList) {
                    createHitcherCollection(routeName);
                }
                return null;
            }
        });
    }

    private void createHitcherCollection(final String routeName) {
        final API api = new API();
        childList = new ArrayList<String>();
        // preparing route collection(child)
        /*api.getHitchhikeMatches(2, 2, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                createHitcherCollectionCallback(api, routeName, childList);
                return null;
            }
        });*/
        if (childList.isEmpty()) {
            childList.add("No hitchhikers");
        }
        routeCollection.put(routeName, childList);
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    public void createGroupListCallback(API api) {

        JSONObject json = api.getResponse();
        try {
            JSONArray arr = json.getJSONArray("routes");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject route = arr.getJSONObject(i);
                int routeID = route.getInt("routeID");
                String startPoint = route.getString("startPoint");
                String endPoint = route.getString("endPoint");
                String timestamp = route.getString("timestamp");
                String routeName = startPoint + " -> " + endPoint;
                groupList.add(routeName/* + ". At " + timestamp*/);
            }

        } catch(JSONException e) {
            e.printStackTrace();
            groupList.add("exception");
        }
        updateAdapter();

    }

    public void createHitcherCollectionCallback(API api, String routeName, List<String> childList) {
        JSONObject json = api.getResponse();
        try {
            JSONArray arr = json.getJSONArray("matches");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject route = arr.getJSONObject(i);
                int userID = route.getInt("userID");
                int relevance = route.getInt("relevance");
                String timestamp = route.getString("timestamp");
                childList.add(Integer.toString(userID));
            }
            if (childList.isEmpty()) {
                childList.add("No hitchhikers");
            }
            routeCollection.put(routeName, childList);
            updateAdapter();
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateAdapter() {
        expListAdapter.notifyDataSetChanged();
    }


}
