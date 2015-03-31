package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dblappdev.hitch.model.User;
import com.dblappdev.hitch.network.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
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

    private String[][] chatMessages;

    private final static int CHAT_ID = 1;
    private final static int CHAT_LIMIT = 50;

    private User sender;

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

        /**
        //Fill array with fake chat data
        for (int i = 0; i<25; i++) {
            for (int j = 0; j<2; j++) {
                //name
                if (j==0) {
                    if (getRandomBoolean()) {
                        chatMessages[i][j] = "<b><font color=#000000>You</font></b>";
                    } else {
                        chatMessages[i][j] = "<u><font color=#cc0029>Friend</font></u>";
                    }
                //message
                } else {
                    chatMessages[i][j] = "<font color=#ffa500>The message</font>";
                }
            }
        }
         */
        loadMessageHistory();
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
        api.getChatMessages(CHAT_ID, CHAT_LIMIT, "0", new Callable<Void>() {
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
            for(int i = 0; i < arr.length(); i++) {
                JSONObject message = arr.getJSONObject(i);

                String sender = message.getString("username");
                String text = message.getString("text");
                appendToMessageHistory(sender, text);

            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
