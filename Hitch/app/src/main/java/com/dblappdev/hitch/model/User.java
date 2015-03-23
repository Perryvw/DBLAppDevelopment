package com.dblappdev.hitch.model;

import android.util.Log;
import com.dblappdev.hitch.network.API;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guusleijsten on 18/03/15.
 */
public class User {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public int getHitchhikes() {
        return hitchhikes;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    private int id;
    private String name;
    private int state;
    private int hitchhikes;
    private String birthdate;
    private String joinedDate;
    private String avatarURL;

    /**
     * Construct users from their id.
     *
     * @param id userID
     * @param instantiate whether you want to load the data from the server.
     */
    public User(int id, boolean instantiate) {
        this.id = id;
        if (id == -1) {
            return;
        }
        if (instantiate && ! load()) {
            Log.e("User", "user could not now be loaded!");
        }
    }

    public boolean load() {
        try {
            JSONObject json = API.getUserData(id);
            name = json.getString("name");
            state = json.getInt("state");
            hitchhikes = json.getInt("hitchhikes");
            birthdate = json.getString("birthdate");
            joinedDate = json.getString("joinedDate");
            avatarURL = json.getString("avatarURL");
            return true;
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Register a user.
     *
     * @param name name
     * @param state state
     * @param age age of the user
     * @return the new id of the user
     */
    public static int registerUser(String name, int state, int age) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String joinedDate = sdfDate.format(now);
        try {
            JSONObject json = API.registerUser(name, state, Integer.toString(2015 - age) + "-01-01", joinedDate);
            return json.getInt("userID");
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setName(String name) {
        API.updateUserData(this.id, name, -1, -1, null, null, null);
    }

    /**
     * @param state 0 for driver, 1 for hiker
     */
    public void setState(int state) {
        API.updateUserData(this.id, null, state, -1, null, null, null);
    }

    /**
     * Increments the times successfully hitchhiked by the user.
     */
    public void incrementHitchhikes() {
        API.updateUserData(this.id, null, -1, hitchhikes++, null, null, null);
    }
}
