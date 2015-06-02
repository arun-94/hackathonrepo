package com.refocus.puneet.axishackathon.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.refocus.puneet.axishackathon.Classes.ChatMessage;
import com.refocus.puneet.axishackathon.R;

import java.util.ArrayList;

public class ChatArrayAdapter extends ArrayAdapter {

    private TextView chatText;
    private ArrayList<ChatMessage> chatMessageList = new ArrayList();
    private RelativeLayout singleMessageContainer;

    public void add(ChatMessage object)
    {
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
        ChatMessage chatMessageObj = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(chatMessageObj.left)
            row = inflater.inflate(R.layout.chat_single_message, parent, false);
        else
            row = inflater.inflate(R.layout.chat_single_message_user, parent, false);
        singleMessageContainer = (RelativeLayout) row.findViewById(R.id.singleMessageContainer);
        chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(chatMessageObj.message);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}