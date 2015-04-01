package com.dblappdev.hitch.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    private String since = "0";
    private ListView listView;
    private ChatArrayAdapter chatArrayAdapter;
    private Boolean left = false;
    private User user;
    private String currentUsername = "";

    Handler mHandler = new Handler();
    ChatThread mThread;

    private final static int CHAT_ID = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        prefs = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        messageText = (EditText) findViewById(R.id.message);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        messageText.requestFocus();

        listView = (ListView) findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

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

        int userID = prefs.getInt(MainActivity.USER_KEY, -1);
        user = new User(userID, true, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                callback();
                return null;
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

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

    public void callback() {
        currentUsername = user.getName();
        loadMessageHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.end();
    }

    public void sendMessage() {
        String message = messageText.getText().toString();
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

        if (username.equals(currentUsername)) {
            left = false;
        } else {
            left = true;
        }
        chatArrayAdapter.add(new ChatMessage(left, username + ": " + message));
    }

    public void loadMessageHistory() {
        final API api = new API();
        int tempSince = Integer.parseInt(since);
        tempSince++;
        since = Integer.toString(tempSince);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch(id) {
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.action_profileView:
                int userID = prefs.getInt(MainActivity.USER_KEY, -1);
                if (userID == -1) {
                    return false;
                }
                intent = new Intent(this, ViewProfileActivity.class);
                intent.putExtra("userID", prefs.getInt(MainActivity.USER_KEY, -1));
                break;
            case R.id.action_openChat:
                intent = new Intent(this, ChatActivity.class);
                break;
            case R.id.action_routeActivity:
                intent = new Intent(this, RouteActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intent);
        return true;
    }
}
