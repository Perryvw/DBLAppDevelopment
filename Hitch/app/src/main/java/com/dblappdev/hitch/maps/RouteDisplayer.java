package com.dblappdev.hitch.maps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

/**
 * This class is used to start a RouteActivity from a start point to an end point. The
 * call to use the functionality is for example as follows:
 *
 * RouteDisplayer.getInstance().showRouteActivity("Eindhoven","Amsterdam",getApplicationContext(),"driving");
 *
 * Created by Ziad on 3/24/2015.
 */
public class RouteDisplayer {

    private static RouteDisplayer rd;
    private Context c;

    /**
     * Returns an instance of the RouteDisplayer. Singleton pattern applied
     * to ensure only a single instance exists.
     *
     * @return instance of class RouteDisplayer
     */
    public static RouteDisplayer getInstance(){
        if (rd == null) {
            rd = new RouteDisplayer();
            return rd;
        }
        return rd;
    }

    /**
     * Method which initiates the a RouteActivity displaying the route
     * from point <@code>start</@code> to <@code>end</@code>.
     *
     * @param start starting point of the route to be displayed
     * @param end end point of the route to be displayed
     */

    /**
     *
     * @param start
     * @param end
     * @param context
     * @param mode
     */
    public void showRouteActivity(String start, String end, Context context, String mode) {
        this.c = context;
        GeoPoint startPoint = getLocGeoPoint(start);
        GeoPoint endPoint = getLocGeoPoint(end);
        new StartRouteActivityTask(context).execute((new GMapV2Direction())
                .getDocumentUrl(new LatLng(startPoint.getLatitudeE6()/1E6,startPoint.getLongitudeE6()/1E6)
                        , new LatLng(endPoint.getLatitudeE6()/1E6,endPoint.getLongitudeE6()/1E6)
                        ,mode));

    }

    /**
     * Returns the GeoPoint of a location.
     * @param location location of which the GeoPoint will be returned.
     *
     * @return GeoPoint of location <@code>location</@code>
     */
    private GeoPoint getLocGeoPoint(String location) {

        double lat;
        double lng;

        Geocoder geoCoder = new Geocoder(c, Locale.getDefault());
        try
        {
            List<Address> addresses = geoCoder.getFromLocationName(location , 1);
            if (addresses.size() > 0)
            {
                GeoPoint p = new GeoPoint(
                        (int) (addresses.get(0).getLatitude() * 1E6),
                        (int) (addresses.get(0).getLongitude() * 1E6));

                lat=p.getLatitudeE6()/1E6;
                lng=p.getLongitudeE6()/1E6;

                Log.d("Latitude", ""+lat);
                Log.d("Longitude", "" + lng);
                return p;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
