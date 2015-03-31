package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.dblappdev.hitch.network.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class ChatActivity extends Activity {

    //Preferences
    private SharedPreferences prefs;

    private EditText messageText;
    private Button sendMessageButton;
    private EditText messageHistoryText;
    private String since = "0";

    Handler mHandler = new Handler();
    ChatThread mThread;

    private final static int CHAT_ID = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        messageText = (EditText) findViewById(R.id.message);
        messageHistoryText = (EditText) findViewById(R.id.messageHistory);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        messageText.requestFocus();

        messageText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If enter is pressed
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if(keyCode == KeyEvent.KEYCODE_ENTER)
                    {
                        sendMessageButton.performClick(); // recherche is my button
                        return true;
                    }
                }
                return false; // when we don't handle other keys, propagate event further
            }
        });

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMessage();
                messageText.setText("");
            }
        });

        loadMessageHistory();
        mThread = new ChatThread(new Runnable() {
            @Override
            public void run() {
                while (ChatThread.RUNNING) {
                    try {
                        Thread.sleep(5000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                loadMessageHistory();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.end();
    }

    public void sendMessage() {
        String message = messageText.getText().toString();
        appendToMessageHistory("You", message);
        int userID = prefs.getInt(MainActivity.USER_KEY, -1);
        if (userID == -1) {
            return;
        }
        new API().insertChatMessage(userID, CHAT_ID, message, null);
    }

    public  void appendToMessageHistory(String username, String message) {
        if (username == null && message == null) {
            username = "Unknown";
            message = "Unknown";
        }
        messageHistoryText.append(username + ":\n");
        messageHistoryText.append(message + "\n");
    }

    public void loadMessageHistory() {
        final API api = new API();
        api.getChatMessages(CHAT_ID, 50, since, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                loadMessageHistoryCallback(api);
                return null;
            }
        });
    }

    public void loadMessageHistoryCallback(API api) {
        JSONObject json = api.getResponse();

        try {
            JSONArray arr = json.getJSONArray("messages");
            for(int i = arr.length()-1; i >= 0; i--) {
                JSONObject message = arr.getJSONObject(i);
                since = message.getString("timestamp");
                String sender = message.getString("username");
                String text = message.getString("text");
                appendToMessageHistory(sender, text);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
