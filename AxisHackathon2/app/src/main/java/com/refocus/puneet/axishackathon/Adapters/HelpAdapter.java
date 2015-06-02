package com.refocus.puneet.axishackathon.Adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.refocus.puneet.axishackathon.Classes.BankAccount;
import com.refocus.puneet.axishackathon.Classes.HelpItem;
import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.Fragments.ChatFragment;
import com.refocus.puneet.axishackathon.R;

import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context context;
    ArrayList<HelpItem> helpItems = new ArrayList<>();
    View view = null;

    public HelpAdapter(Context applicationContext, ArrayList<HelpItem> items)
    {
        this.context = applicationContext;
        helpItems.addAll(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        if (i == TYPE_HEADER)
        {
            view = LayoutInflater.from(context).inflate(R.layout.help_header, viewGroup, false);
            return new VHHeader(view);
        }
        else
        {
            if (i == TYPE_ITEM)
            {
                view = LayoutInflater.from(context).inflate(R.layout.help_item, viewGroup, false);
                return new VHItem(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        HelpItem helpItem = helpItems.get(i);

        if (viewHolder instanceof VHHeader)
        {
            //((VHHeader) viewHolder).balanceAmt.setText(bankAccount.Balance + "/- ");
        }
        else
        {
            if (viewHolder instanceof VHItem)
            {
                if (helpItem != null)
                {
                    ((VHItem) viewHolder).listName.setText("" + helpItem.title);
                    ((VHItem) viewHolder).listDescription.setText(helpItem.description);
                }
            }
        }
    }


    @Override
    public int getItemCount()
    {
        return helpItems.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position))
        {
            return TYPE_HEADER;
        }
        else
        {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionHeader(int position)
    {
        if (position == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    class VHHeader extends RecyclerView.ViewHolder
    {
        TextView accNo;
        TextView balanceAmt;

        public VHHeader(View itemView)
        {
            super(itemView);
            this.accNo = (TextView) itemView.findViewById(R.id.tv_accNo);
            this.balanceAmt = (TextView) itemView.findViewById(R.id.tv_balance);
        }
    }

    class VHItem extends RecyclerView.ViewHolder
    {
        TextView listName;
        TextView listDescription;

        public VHItem(View itemView)
        {
            super(itemView);
            this.listName = (TextView) itemView.findViewById(R.id.helpTitle);
            this.listDescription = (TextView) itemView.findViewById(R.id.helpDescription);
         }
    }
}
