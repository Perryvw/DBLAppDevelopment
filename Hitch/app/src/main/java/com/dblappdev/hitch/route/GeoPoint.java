package com.dblappdev.hitch.route;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ziad on 3/28/2015.
 */
public class GeoPoint implements Parcelable {

    private double longitude;
    private String city;
    private String country;

    public GeoPoint(double latitude, double longitude, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
    }

    private double latitude;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude/1E6;
    }

    public double getLongitude() {
        return longitude/1E6;
    }


    protected GeoPoint(Parcel in) {
        longitude = in.readDouble();
        city = in.readString();
        country = in.readString();
        latitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeDouble(latitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<GeoPoint> CREATOR = new Parcelable.Creator<GeoPoint>() {
        @Override
        public GeoPoint createFromParcel(Parcel in) {
            return new GeoPoint(in);
        }

        @Override
        public GeoPoint[] newArray(int size) {
            return new GeoPoint[size];
        }
    };
}
