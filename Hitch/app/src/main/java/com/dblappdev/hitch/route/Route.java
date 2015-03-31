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
    private int ownerID;
    private String depTime;
    private String eta;
    private ArrayList<LatLng> directions;

    public Route(GeoPoint start, GeoPoint end, int ownerID, String depTime, String eta) {
        this.start = start;
        this.end = end;
        this.ownerID = ownerID;
        this.depTime = depTime;
        this.eta = eta;
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

    public int getOwnerID() {
        return ownerID;
    }

    public GeoPoint getStart() {
        return start;
    }

    public GeoPoint getEnd() {
        return end;
    }

    protected Route(Parcel in) {
        start = (GeoPoint) in.readValue(GeoPoint.class.getClassLoader());
        end = (GeoPoint) in.readValue(GeoPoint.class.getClassLoader());
        ownerID = in.readInt();
        depTime = in.readString();
        eta = in.readString();
        if (in.readByte() == 0x01) {
            directions = new ArrayList<LatLng>();
            in.readList(directions, LatLng.class.getClassLoader());
        } else {
            directions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(start);
        dest.writeValue(end);
        dest.writeInt(ownerID);
        dest.writeString(depTime);
        dest.writeString(eta);
        if (directions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(directions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
