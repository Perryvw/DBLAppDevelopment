package com.dblappdev.hitch.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;
import com.dblappdev.hitch.adapter.ListAdapter;
import com.dblappdev.hitch.model.ListViewItem;
import com.dblappdev.hitch.network.API;
import com.dblappdev.hitch.route.RouteDisplayer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class HitchFragment extends ListFragment {

    private List<ListViewItem> mItems;        // ListView items list
    private JSONObject json;
    private SharedPreferences prefs;
    Resources resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hitcher, container, false);

        prefs = getActivity().getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        // initialize the items list
        mItems = new ArrayList<ListViewItem>();
        resources = getResources();
        Drawable avatar = resources.getDrawable(R.drawable.ic_launcher);
        Drawable stars = resources.getDrawable(R.drawable.stars);
        Drawable arrow = resources.getDrawable(R.drawable.arrow_right);

        // Creates the List
        createHitchList();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);

        // do something
        //Toast.makeText(getActivity(), item.name, Toast.LENGTH_SHORT).show();
        final API api = new API();
        int routeID = ((ListViewItem)l.getItemAtPosition((int)id)).getRouteID();
        api.getRouteData(routeID, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                json = api.getResponse();
                try {
                    RouteDisplayer.getInstance().showRouteActivity(json.get("startPoint").toString(), json.get("endPoint").toString(),
                            json.getInt("userID"),"11:00","15:00",getActivity().getApplicationContext());
                } catch(JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    private void createHitchList () {
        final API api = new API();
        int userID = prefs.getInt(MainActivity.USER_KEY, -1);
        if (userID == -1) return;
        api.getMatchingDrivers(userID, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                createHitchListCallback(api);
                return null;
            }
        });
    }

    private void createHitchListCallback(API api) {
        json = api.getResponse();
        try {
            JSONArray array = json.getJSONArray("routes");
            mItems = new ArrayList<ListViewItem>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject match = array.getJSONObject(i);
                int driverID = match.getInt("userID");
                int routeID = match.getInt("routeID");
                int relevance = match.getInt("relevance");
                mItems.add(new ListViewItem(routeID, match.getString("routeName"), match.getString("userName"),
                        "TIME", resources.getDrawable(R.drawable.ic_launcher), resources.getDrawable(R.drawable.stars),
                        resources.getDrawable(R.drawable.arrow_right)));
            }

            // ListAdapter
            ListAdapter listAdapter = new ListAdapter(getActivity(), mItems);

            // initialize and set the list adapter
            setListAdapter(listAdapter);
        } catch(JSONException e) {
            mItems.add(new ListViewItem( "test 2 ","das",
                    "TIMEEXC", resources.getDrawable(R.drawable.ic_launcher), resources.getDrawable(R.drawable.stars),
                    resources.getDrawable(R.drawable.arrow_right)));
            e.printStackTrace();
        }

        //getListAdapter().notifyAll();
    }
}

