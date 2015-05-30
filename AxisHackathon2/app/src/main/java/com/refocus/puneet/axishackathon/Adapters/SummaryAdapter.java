package com.refocus.puneet.axishackathon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.R;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context context;
    ArrayList<Transaction> transactions = new ArrayList<>();
    View view = null;
    int count;
    int width;

    public SummaryAdapter(Context applicationContext)
    {
        this.context = applicationContext;
        transactions.add(new Transaction());
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", true));
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", false));
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", false));
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", true));

    }

/*
    public void addItem(Transaction newVenue)
    {
        if (count > transactions.size() - 1)
        {
            transactions.add(newVenue);
            notifyItemInserted(count);
        } else
        {
            transactions.set(count, newVenue);
            notifyItemChanged(count);
        }
        count++;
    }
*/


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        if (i == TYPE_HEADER)
        {
            view = LayoutInflater.from(context).inflate(R.layout.summary_header, viewGroup, false);
            return new VHHeader(view);
        }
        else if (i == TYPE_ITEM)
        {
            view = LayoutInflater.from(context).inflate(R.layout.summary_item, viewGroup, false);
            return new VHItem(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        Transaction transaction = transactions.get(i);

        if (viewHolder instanceof VHHeader)
        {
            if(!transaction.real)
                ((VHHeader) viewHolder).accNo.setText("5050505050505");
                ((VHHeader) viewHolder).balanceAmt.setText("Rs. 5000000");
        }
        else if (viewHolder instanceof VHItem)
        {
            if (transaction != null)
            {
                ((VHItem) viewHolder).listName.setText("" + transaction.name);
                ((VHItem) viewHolder).listAmt.setText(transaction.amt);
                ((VHItem) viewHolder).listType.setText("" + transaction.type);
                ((VHItem) viewHolder).listDate.setText(transaction.date);
                if(transaction.credit)
                    ((VHItem) viewHolder).listAmt.setTextColor(context.getResources().getColor(R.color.purple_300));
                else
                    ((VHItem) viewHolder).listAmt.setTextColor(context.getResources().getColor(R.color.red_error));
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return transactions.size();
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
            return true;
        else
            return false;
    }

    public void setItem(int i, Transaction venue)
    {
        transactions.set(i, venue);
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

        ImageView listImageBG;
        TextView listName;
        TextView listAmt;
        TextView listDate;
        TextView listType;
        public VHItem(View itemView)
        {
            super(itemView);
            this.listImageBG = (ImageView) itemView.findViewById(R.id.list_transaction_image);
            this.listName = (TextView) itemView.findViewById(R.id.list_transaction_name);
            this.listAmt = (TextView) itemView.findViewById(R.id.list_transaction_amount);
            this.listDate = (TextView) itemView.findViewById(R.id.list_transaction_date);
            this.listType = (TextView) itemView.findViewById(R.id.list_transaction_type);
        }
    }
}
