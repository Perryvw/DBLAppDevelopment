package com.dblappdev.hitch.route;

/**
 * Created by Ziad on 3/28/2015.
 */
public class GeoPoint {

    private double latitude;

    public double getLongitude() {
        return longitude/1E6;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude/1E6;
    }

    private double longitude;
    private String city;
    private String country;

    public GeoPoint(double latitude, double longitude, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

}
