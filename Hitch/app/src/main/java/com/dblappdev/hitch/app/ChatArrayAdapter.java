package com.dblappdev.hitch.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView message;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private LinearLayout singleMessageContainer;

    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
        }

        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        message = (TextView) row.findViewById(R.id.singleMessage);
        message.setText(chatMessageObj.message);
        if (chatMessageObj.left) {
            message.setPadding(10,10,10,30);
        } else {
            message.setPadding(10,30,10,10);
        }

        singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
        return row;
    }
}