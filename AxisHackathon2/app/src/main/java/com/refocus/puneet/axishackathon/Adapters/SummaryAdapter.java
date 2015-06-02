package com.refocus.puneet.axishackathon.Adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.refocus.puneet.axishackathon.Classes.BankAccount;
import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.Fragments.ChatFragment;
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
    BankAccount bankAccount;

    public SummaryAdapter(Context applicationContext, ArrayList<Transaction> recievedTransactions, BankAccount account)
    {
        this.context = applicationContext;
        transactions.add(new Transaction());
        transactions.addAll(recievedTransactions);
        bankAccount = account;
        /*
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", true));
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", false));
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", false));
        transactions.add(new Transaction("Strin", "24-4-4", "REstaurant", "Rs. 50000", true));*/

    }


    public void addItem(Transaction newVenue)
    {
        transactions.add(newVenue);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        if (i == TYPE_HEADER)
        {
            view = LayoutInflater.from(context).inflate(R.layout.summary_header, viewGroup, false);
            return new VHHeader(view);
        }
        else
        {
            if (i == TYPE_ITEM)
            {
                view = LayoutInflater.from(context).inflate(R.layout.summary_item, viewGroup, false);
                return new VHItem(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        Transaction transaction = transactions.get(i);

        if (viewHolder instanceof VHHeader)
        {
            if (!transaction.real)
            {
                ((VHHeader) viewHolder).accNo.setText(bankAccount.AccNo);
            }
            ((VHHeader) viewHolder).balanceAmt.setText(bankAccount.Balance);
        }
        else
        {
            if (viewHolder instanceof VHItem)
            {
                if (transaction != null)
                {
                    ((VHItem) viewHolder).listName.setText("" + transaction.name);
                    ((VHItem) viewHolder).listAmt.setText(transaction.amt);
                    ((VHItem) viewHolder).listType.setText("" + transaction.type);
                    ((VHItem) viewHolder).listDate.setText(transaction.date);
                    Log.d("ISCREDIT", "" + transaction.credit);
                    if (!transaction.credit)
                    {
                        ((VHItem) viewHolder).listAmt.setTextColor(context.getResources().getColor(R.color.red_error));
                        if (transaction.type.startsWith("Rest"))
                        {
                            ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_restaurant);
                        }
                        else
                        {
                            if (transaction.type.startsWith("Cin"))
                            {
                                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_movie);
                            }
                            else
                            {
                                if (transaction.type.startsWith("With"))
                                {
                                    ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_atm);
                                }
                                else
                                {
                                    if (transaction.type.startsWith("Shop"))
                                    {
                                        ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_shop);
                                    }
                                    else
                                    {
                                        if (transaction.type.startsWith("Rech"))
                                        {
                                            ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_recharge);
                                        }
                                        else
                                        {
                                            if (transaction.type.startsWith("Trav"))
                                                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_travel);
                                            else
                                                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_debit);

                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        ((VHItem) viewHolder).listAmt.setTextColor(context.getResources().getColor(R.color.green));
                        if (transaction.type.startsWith("Salar"))
                        {
                            ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_salary);
                        }
                        else
                        {
                            ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_credit);
                        }
                    }
                }
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
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setItem(int i, Transaction venue)
    {
        transactions.set(i, venue);
    }

    class VHHeader extends RecyclerView.ViewHolder
    {
        TextView accNo;
        TextView balanceAmt;
        FloatingActionButton foxFab;
        public VHHeader(View itemView)
        {
            super(itemView);
            this.accNo = (TextView) itemView.findViewById(R.id.tv_accNo);
            this.balanceAmt = (TextView) itemView.findViewById(R.id.tv_balance);
            this.foxFab = (FloatingActionButton) itemView.findViewById(R.id.button_fab);
            foxFab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ((Activity) context).getFragmentManager().beginTransaction().replace(R.id.container, ChatFragment.newInstance(), "ChatFragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack("SummaryFragment")
                            .commit();
                }
            });
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
