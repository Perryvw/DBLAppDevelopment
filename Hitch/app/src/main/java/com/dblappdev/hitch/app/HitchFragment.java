package com.dblappdev.hitch.app;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;
import com.dblappdev.hitch.adapter.ListAdapter;
import com.dblappdev.hitch.model.ListViewItem;
import com.dblappdev.hitch.network.API;
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
    Resources resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_hitcher, container, false);

        // initialize the items list
        mItems = new ArrayList<ListViewItem>();
        resources = getResources();
        Drawable avatar = resources.getDrawable(R.drawable.ic_launcher);
        Drawable stars = resources.getDrawable(R.drawable.stars);
        Drawable arrow = resources.getDrawable(R.drawable.arrow_right);

        // Sample additions
        mItems.add(new ListViewItem("Eindhoven-Amsterdam", "Driver Name Surname", "12:00", avatar, stars, arrow));
        mItems.add(new ListViewItem("Eindhoven-Amsterdam", "Driver Name Surname", "13:00", avatar, stars, arrow));
        mItems.add(new ListViewItem("Eindhoven-Amsterdam", "Driver Name Surname", "14:00", avatar, stars, arrow));

        // Creates the List
        createHitchList();

        // ListAdapter
        ListAdapter listAdapter = new ListAdapter(getActivity(), mItems);

        // initialize and set the list adapter
        setListAdapter(listAdapter);

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
        Toast.makeText(getActivity(), item.name, Toast.LENGTH_SHORT).show();
    }

    private void createHitchList () {
        final API api = new API();
        api.getMatchingDrivers(/*userID*/2, new Callable<Void>() {
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
            for (int i = 0; i < array.length(); i++) {
                JSONObject match = array.getJSONObject(i);
                int driverID = match.getInt("userID");
                int routeID = match.getInt("routeID");
                int relevance = match.getInt("relevance");
                mItems.add(new ListViewItem( Integer.toString(routeID), Integer.toString(driverID),
                        "TIME", resources.getDrawable(R.drawable.ic_launcher), resources.getDrawable(R.drawable.stars),
                        resources.getDrawable(R.drawable.arrow_right)));
            }
            mItems.add(new ListViewItem( "test 2 ","das",
                    "TIME", resources.getDrawable(R.drawable.ic_launcher), resources.getDrawable(R.drawable.stars),
                    resources.getDrawable(R.drawable.arrow_right)));
        } catch(JSONException e) {
            e.printStackTrace();
        }
        mItems.add(new ListViewItem( "test 2 ","das",
                "TIME", resources.getDrawable(R.drawable.ic_launcher), resources.getDrawable(R.drawable.stars),
                resources.getDrawable(R.drawable.arrow_right)));

        getListAdapter().notifyAll();
    }
}

