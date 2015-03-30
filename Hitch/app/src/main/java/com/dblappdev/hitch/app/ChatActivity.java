package com.dblappdev.hitch.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

/**
 * Created by s128232 on 10-3-2015.
 */
public class ChatActivity extends Activity {

    private EditText messageText;
    private Button sendMessageButton;
    private EditText messageHistoryText;

    //TESTING
    private Random random;
    private String[][] chatMessages;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        messageText = (EditText) findViewById(R.id.message);
        messageHistoryText = (EditText) findViewById(R.id.messageHistory);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);
        messageText.requestFocus();



        //TESTING
        chatMessages = new String[25][2];
        random = new Random();
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
            }
        });

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
        loadMessageHistory();
    }

    public void sendMessage() {
        String message = messageText.getText().toString();
        appendToMessageHistory("You", message);
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
        for (int i = 0; i<chatMessages.length; i++) {
            messageHistoryText.append(Html.fromHtml(chatMessages[i][0]) + ":\n");
            messageHistoryText.append(Html.fromHtml(chatMessages[i][1]) + "\n");
        }
    }

    //TESTING
    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }
}
