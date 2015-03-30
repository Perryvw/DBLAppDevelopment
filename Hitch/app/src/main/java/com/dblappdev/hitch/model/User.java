package com.dblappdev.hitch.model;

import android.util.Log;
import com.dblappdev.hitch.network.API;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

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
    private Callable<Void> callback;

    /**
     * Construct users from their id.
     *
     * @param id userID
     * @param instantiate whether you want to load the data from the server.
     * @param callback null if not instantiating, otherwise the function you want to call after loading the data.
     */
    public User(int id, boolean instantiate, Callable<Void> callback) {
        this.callback = callback;
        this.id = id;
        if (id == -1) {
            return;
        }
        if (! instantiate) {
            return;
        }
        final API api = new API();
        api.getUserData(id, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                load(api);
                return null;
            }
        });
    }

    public boolean load(API api) {
        JSONObject json = api.getResponse();
        try {
            name = json.getString("name");
            state = json.getInt("state");
            hitchhikes = json.getInt("hitchhikes");
            birthdate = json.getString("birthdate");
            joinedDate = json.getString("joinedDate");
            avatarURL = json.getString("avatarURL");
            if (callback != null) {
                try {
                    callback.call();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
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
    public static void registerUser(String name, int state, int age, String phone, Callable<Void> callback) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
        Date now = new Date();
        String joinedDate = sdfDate.format(now);
        new API().registerUser(name, state, phone, Integer.toString(2015 - age) + "-01-01", joinedDate, callback);
    }

    public void setName(String name) {
        new API().updateUserData(this.id, name, -1, -1, null, null, null);
    }

    /**
     * @param state 0 for driver, 1 for hiker
     */
    public void setState(int state) {
        new API().updateUserData(this.id, null, state, -1, null, null, null);
    }

    /**
     * Increments the times successfully hitchhiked by the user.
     */
    public void incrementHitchhikes() {
        new API().updateUserData(this.id, null, -1, hitchhikes++, null, null, null);
    }
}
