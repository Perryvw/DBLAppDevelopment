package com.dblappdev.hitch.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Class containing route details.
 *
 * Created by Ziad on 3/30/2015.
 */
public class Route implements Parcelable {

    private GeoPoint start;
    private GeoPoint end;
    private String owner;
    private String depTime;
    private String eta;
    private ArrayList<LatLng> directions;

    public Route(GeoPoint start, GeoPoint end, String owner, String depTime, String eta) {
        this.start = start;
        this.end = end;
        this.owner = owner;
        this.depTime = depTime;
        this.eta = eta;
    }

    public Route(Parcel in) {

    }

    public void setDirections(ArrayList<LatLng> directions) {
        this.directions = directions;
    }

    public ArrayList<LatLng> getDirections() {
        return directions;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getEta() {
        return eta;
    }

    public String getOwner() {
        return owner;
    }

    public GeoPoint getStart() {
        return start;
    }

    public GeoPoint getEnd() {
        return end;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
