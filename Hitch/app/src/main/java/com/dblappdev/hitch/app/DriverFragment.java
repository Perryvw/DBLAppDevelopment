package com.dblappdev.hitch.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
    ExpandableListView expListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_driver, container, false);

        createGroupList();

        createCollection();

        ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.route_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, groupList, routeCollection);
        expListView.setAdapter(expListAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getActivity().getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);
        int userID = prefs.getInt(MainActivity.USER_KEY, -1);

        API.getUserRoutes(userID, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });

        return rootView;
    }

    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("HP");
        groupList.add("Dell");
        groupList.add("Lenovo");
        groupList.add("Sony");
        groupList.add("HCL");
        groupList.add("Samsung");
    }

    private void createCollection() {
        // preparing route collection(child)
        String[] hpModels = { "HP Pavilion G6-2014TX", "ProBook HP 4540",
                "HP Envy 4-1025TX" };
        String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
        String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
                "ThinkPad X Series", "Ideapad Z Series" };
        String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
                "VAIO S Series", "VAIO YB Series" };
        String[] dellModels = { "Inspiron", "Vostro", "XPS" };
        String[] samsungModels = { "NP Series", "Series 5", "SF Series" };

        routeCollection = new LinkedHashMap<String, List<String>>();

        for (String route : groupList) {
            if (route.equals("HP")) {
                loadChild(hpModels);
            } else if (route.equals("Dell"))
                loadChild(dellModels);
            else if (route.equals("Sony"))
                loadChild(sonyModels);
            else if (route.equals("HCL"))
                loadChild(hclModels);
            else if (route.equals("Samsung"))
                loadChild(samsungModels);
            else
                loadChild(lenovoModels);

            routeCollection.put(route, childList);
        }
    }

    private void loadChild(String[] routeModels) {
        childList = new ArrayList<String>();
        for (String model : routeModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
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
