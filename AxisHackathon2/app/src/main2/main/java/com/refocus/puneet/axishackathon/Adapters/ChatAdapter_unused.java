package com.refocus.puneet.axishackathon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.refocus.puneet.axishackathon.Classes.ChatMessage;
import com.refocus.puneet.axishackathon.R;

import java.util.ArrayList;

public class ChatAdapter_unused extends RecyclerView.Adapter
{
    private static final int TYPE_LEFT = 1;
    private static final int TYPE_RIGHT = 2;

    Context context;
    private ArrayList<ChatMessage> chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;

    View view = null;

    public ChatAdapter_unused(Context applicationContext)
    {
        this.context = applicationContext;
    }

    public void addItem(ChatMessage message)
    {
        chatMessageList.add(message);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
                view = LayoutInflater.from(context).inflate(R.layout.chat_single_message, viewGroup, false);
                return new VHChat(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        ((VHChat) viewHolder).text.setText("" + chatMessageList.get(i));
    }

    @Override
    public int getItemCount()
    {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
            if (position % 2 != 0)
            {
                return TYPE_LEFT;
            }
            return TYPE_RIGHT;
    }

    public void setItem(int i, ChatMessage message)
    {
        chatMessageList.set(i, message);
    }

    private class VHChat extends RecyclerView.ViewHolder
    {
        TextView text;
        public VHChat(View view)
        {
            super(view);
            this.text = (TextView) view.findViewById(R.id.singleMessage);
        }
    }
}
