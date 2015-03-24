package com.dblappdev.hitch.network;

import android.util.Log;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by guusleijsten on 16/03/15.
 */
public class API {
    /** Function details */
    private static final String[] FUNCTIONS = {"GetUserData", "RegisterUser", "UpdateUserData", "GetUserRating",
            "GetUserRatingByUser", "GetUserRoutes", "AddUserRoute", "DeleteUserRoute", "UpdateUserRoute",
            "GetHitchhikeData", "GetUserHitchhikeData", "AddUserHitchhikeData", "RemoveUserHitchhikeData",
            "AddUserToChat", "RemoveUserFromChat", "CreateChatbox", "GetChatMessages", "GetChatBoxes",
            "GetMatchingDrivers", "GetHitchhikeMatches"};
    public static final int GET_USER_DATA = 0;
    public static final int REGISTER_USER = 1;
    public static final int UPDATE_USER_DATA = 2;
    public static final int GET_USER_RATING = 3;
    public static final int GET_USER_RATING_BY_USER = 4;
    public static final int GET_USER_ROUTES = 5;
    public static final int ADD_USER_ROUTE = 6;
    public static final int DELETE_USER_ROUTE = 7;
    public static final int UPDATE_USER_ROUTE = 8;
    public static final int GET_HITCHHIKE_DATA = 9;
    public static final int GET_USER_HITCHHIKE_DATA = 10;
    public static final int ADD_USER_HITCHHIKE_DATA = 11;
    public static final int REMOVE_USER_HITCHHIKE_DATA = 12;
    public static final int ADD_USER_TO_CHAT = 13;
    public static final int REMOVE_USER_FROM_CHAT = 14;
    public static final int CREATE_CHATBOX = 15;
    public static final int GET_CHAT_MESSAGES = 16;
    public static final int GET_CHAT_BOXES = 17;
    public static final int GET_MATCHING_DRIVERS = 18;
    public static final int GET_HITCHHIKE_MATCHES = 19;

    private static JSONObject RESPONSE;

    public static void setResponse(JSONObject response) {
        RESPONSE = response;
    }

    public static JSONObject getResponse() {
        return RESPONSE;
    }

    private static void commit(String[] params) {
        try {
            new getUrlResponseTask().execute(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void commit(String[] params, Callable<Void> callback) {
        try {
            new getUrlResponseTask().execute(callback, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the data of a user by id.
     *
     * @param userID the id of the user
     */
    public static void getUserData(int userID, Callable<Void> callback) {
        String[] params = new String[2];
        params[0] = "func=" + FUNCTIONS[GET_USER_DATA];
        params[1] = "userID=" + Integer.toString(userID);

        commit(params, callback);
    }

    /**
     * Registers a new user.
     *
     *
     */
    public static void registerUser(String name, int state, String birthdate, String joinedDate, Callable<Void> callback) {
        String[] params = new String[7];
        params[0] = "func=" + FUNCTIONS[REGISTER_USER];
        params[1] = "name=" + urlEncode(name);
        params[2] = "state=" + Integer.toString(state);
        params[3] = "hitchhikes=0";
        params[4] = "birthDate=" + birthdate;
        params[5] = "joinedDate=" + joinedDate;
        params[6] = "avatarURL";

        commit(params, callback);
    }

    /**
     * Retrieves the routes for a given user.
     *
     * @param userID the id of the user for which you want to retrieve his or her routes
     * @param callback the callback
     */
    public static void getUserRoutes(int userID, Callable<Void> callback) {
        String[] params = new String[2];
        params[0] = "func=" + FUNCTIONS[GET_USER_ROUTES];
        params[1] = "userID=" + Integer.toString(userID);

        commit(params, callback);
    }

    /**
     * Retrieves what hitchikers will match some given route.
     *
     * @param routeID the id of the user for which you want to retrieve his or her routes
     * @param timeWindow the time window which you want to search for
     * @param callback the callback
     */
    public static void getHitchhikeMatches(int routeID, int timeWindow, Callable<Void> callback) {
        String[] params = new String[2];
        params[0] = "func=" + FUNCTIONS[GET_HITCHHIKE_MATCHES];
        params[1] = "routeID=" + Integer.toString(routeID);
        params[1] = "timeWindow=" + Integer.toString(timeWindow);
        commit(params, callback);
    }

    /**
     * Updates a users fields.
     *
     * @param userId id of user to update
     * @param name (optinal) the name, null if not used
     * @param state (optional) -1 if not used
     * @param hitchhikes (optional -1 if not used
     * @param birthdate (optional) null if not used
     * @param joinedDate (optional) null if not used
     * @param avatarURL (optional) null if not used
     * @return server response
     */
    public static void updateUserData(int userId, String name, int state, int hitchhikes, String birthdate, String joinedDate, String avatarURL) {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("func=" + FUNCTIONS[UPDATE_USER_DATA]);
        arr.add("userID=" + Integer.toString(userId));
        if (name != null) {
            arr.add("name=" + urlEncode(name));
        }
        if (state != -1) {
            arr.add("state=" + Integer.toString(state));
        }
        if (hitchhikes != -1) {
            arr.add("hitchhikes=" + Integer.toString(hitchhikes));
        }
        if (birthdate != null) {
            arr.add("birthdate=" + birthdate);
        }
        if (joinedDate != null) {
            arr.add("joinedDate" + joinedDate);
        }
        if (avatarURL != null) {
            arr.add("avatarURL");
        }
        //convert to array
        String[] params = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            params[i] = arr.get(i);
        }
        commit(params);
    }

    private static String urlEncode(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
