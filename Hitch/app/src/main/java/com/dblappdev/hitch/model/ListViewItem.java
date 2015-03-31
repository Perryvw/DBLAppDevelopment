package com.dblappdev.hitch.model;

import android.graphics.drawable.Drawable;

/**
 * Created by s138308 on 29-3-2015.
 */
public class ListViewItem {
    public final int routeID;
    public final String routeName;    // String for Route name
    public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String name;         // the text for the ListView item name
    public final String time;         // the text for the ListView item time
    public final Drawable stars;      // the drawable for the ListView item ImageView
    public final Drawable arrow;      // the drawable for the ListView item Arrow

    public ListViewItem(int routeID, String routeName, String name, String time, Drawable icon, Drawable stars, Drawable arrow) {
        this.routeID = routeID;
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.stars = stars;
        this.routeName = routeName;
        this.arrow = arrow;
    }

    public int getRouteID() {
        return routeID;
    }
}