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
     */
    public User(int id) {
        this.id = id;
        if (! load()) {
            Log.e("User", "user could not now be loaded!");
        }
    }

    /**
     * Construct user from data and registers.
     *
     * @param name users name
     * @param state users state
     * @param birthdate users date of birth
     */
    public User(String name, int state, String birthdate) {
        this.name = name;
        this.state = state;
        this.birthdate = birthdate;
        //get current date
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        this.joinedDate = sdfDate.format(now);
        //Register the user and set the retrieved id.
        this.id = register(name, state, birthdate, joinedDate);
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

    public String toString() {
        return "{id: " + Integer.toString(id) + ", name: " + name + ", state: " + Integer.toString(state) + "}";
    }

    public int register(String name, int state, String birthdate, String joinedDate) {
        try {
            JSONObject json = API.registerUser(name, state, birthdate, joinedDate);
            return json.getInt("userID");
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
