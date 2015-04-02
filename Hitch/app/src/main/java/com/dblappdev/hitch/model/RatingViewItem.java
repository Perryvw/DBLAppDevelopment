package com.dblappdev.hitch.model;

import android.graphics.drawable.Drawable;

/**
 * Created by guusleijsten on 02/04/15.
 */
public class RatingViewItem {
    public final String name;
    public final String comment;
    public final int rating;

    public RatingViewItem(String name, String comment, int rating) {
        this.name = name;
        this.comment = comment;
        this.rating = rating;
    }
}
