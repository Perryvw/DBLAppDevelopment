package com.dblappdev.hitch.maps;

/**
 * Created by Ziad on 3/28/2015.
 */
public class GeoPoint {

    private double latitude;
    private double longitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitudeE6() {
        return latitude;
    }

    public double getLongitudeE6() {
        return longitude;
    }
}
