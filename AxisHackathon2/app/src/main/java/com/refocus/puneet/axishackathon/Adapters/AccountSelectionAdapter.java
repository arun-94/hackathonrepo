package com.refocus.puneet.axishackathon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.refocus.puneet.axishackathon.Classes.BankAccount;
import com.refocus.puneet.axishackathon.R;

import java.util.ArrayList;

public class AccountSelectionAdapter extends RecyclerView.Adapter
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context context;
    ArrayList<BankAccount> bankAccounts = new ArrayList<>();
    RelativeLayout mainLayout;
    View view = null;

    public AccountSelectionAdapter(Context applicationContext)
    {
        this.context = applicationContext;
        bankAccounts.add(new BankAccount("ACT000001", "24-4-4", "Rs. 500000"));
        bankAccounts.add(new BankAccount("ACT000002", "24-4-4", "Rs. 460000"));
        bankAccounts.add(new BankAccount("ACT000003", "24-4-4", "Rs. 900"));
        bankAccounts.add(new BankAccount("ACT000004", "24-4-4", "Rs. 740"));
        bankAccounts.add(new BankAccount("ACT000004", "24-4-4", "Rs. 740"));
        bankAccounts.add(new BankAccount("ACT000004", "24-4-4", "Rs. 740"));
        bankAccounts.add(new BankAccount("ACT000004", "24-4-4", "Rs. 740"));
        bankAccounts.add(new BankAccount("ACT000004", "24-4-4", "Rs. 740"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        view = LayoutInflater.from(context).inflate(R.layout.account_selection_item, viewGroup, false);
        return new VHItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        BankAccount bankAccount = bankAccounts.get(i);
        switch (i % 5)
        {
            case 0:
                view.setBackgroundColor(context.getResources().getColor(R.color.acsel_1));
                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_chat_char_2);
                break;
            case 1:
                view.setBackgroundColor(context.getResources().getColor(R.color.acsel_2));
                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_chat_char_3);
                break;
            case 2:
                view.setBackgroundColor(context.getResources().getColor(R.color.acsel_3));
                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_chat_char_5);
                break;
            case 3:
                view.setBackgroundColor(context.getResources().getColor(R.color.acsel_4));
                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_chat_char_4);

                break;
            case 4:
                view.setBackgroundColor(context.getResources().getColor(R.color.acsel_5));
                ((VHItem) viewHolder).listImageBG.setImageResource(R.drawable.ic_chat_char_1);
                break;

        }
        ((VHItem) viewHolder).listAccNo.setText(bankAccount.AccNo);
        ((VHItem) viewHolder).listBalance.setText(bankAccount.Balance);
    }

    @Override
    public int getItemCount()
    {
        return bankAccounts.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return TYPE_ITEM;
    }

    public void setItem(int i, BankAccount venue)
    {
        bankAccounts.set(i, venue);
    }
}

class VHItem extends RecyclerView.ViewHolder
{

    ImageView listImageBG;
    TextView listAccNo;
    TextView listBalance;

    public VHItem(View itemView)
    {
        super(itemView);
        this.listImageBG = (ImageView) itemView.findViewById(R.id.account_select_img);
        this.listAccNo = (TextView) itemView.findViewById(R.id.account_select_acno);
        this.listBalance = (TextView) itemView.findViewById(R.id.account_select_balance);
    }
}

