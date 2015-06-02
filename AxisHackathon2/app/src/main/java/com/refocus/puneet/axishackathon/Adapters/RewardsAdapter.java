package com.refocus.puneet.axishackathon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.refocus.puneet.axishackathon.Classes.RewardsItem;
import com.refocus.puneet.axishackathon.R;

import java.util.ArrayList;

public class RewardsAdapter extends RecyclerView.Adapter
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context context;
    ArrayList<RewardsItem> rewardsItems = new ArrayList<>();
    View view = null;

    public RewardsAdapter(Context applicationContext, ArrayList<RewardsItem> items)
    {
        this.context = applicationContext;
        rewardsItems.addAll(items);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        if (i == TYPE_HEADER)
        {
            view = LayoutInflater.from(context).inflate(R.layout.reward_header, viewGroup, false);
            return new VHHeader(view);
        } else
        {
            if (i == TYPE_ITEM)
            {
                view = LayoutInflater.from(context).inflate(R.layout.reward_item, viewGroup, false);
                return new VHItem(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        RewardsItem rewardItem = rewardsItems.get(i);

        if (viewHolder instanceof VHHeader)
        {
            //((VHHeader) viewHolder).balanceAmt.setText(bankAccount.Balance + "/- ");
        } else
        {
            if (viewHolder instanceof VHItem)
            {
                if (rewardItem != null)
                {
                    ((VHItem) viewHolder).gridName.setText("" + rewardItem.title);
                    ((VHItem) viewHolder).gridPoints.setText(rewardItem.points + " Points");

                    if(rewardItem.imagetype == 1) {
                        ((VHItem) viewHolder).image.setImageResource(R.drawable.ic_restaurant);
                    } else if(rewardItem.imagetype == 2) {
                        ((VHItem) viewHolder).image.setImageResource(R.drawable.ic_coffee);
                    } else if (rewardItem.imagetype == 3) {
                        ((VHItem) viewHolder).image.setImageResource(R.drawable.ic_atm);
                    } else if (rewardItem.imagetype == 4) {
                        ((VHItem) viewHolder).image.setImageResource(R.drawable.ic_movie);
                    } else if (rewardItem.imagetype == 5) {
                        ((VHItem) viewHolder).image.setImageResource(R.drawable.ic_shop);
                    } else if (rewardItem.imagetype == 0) {
                        ((VHItem) viewHolder).image.setImageResource(R.drawable.ic_travel);
                    }
                }
            }
        }
    }


    @Override
    public int getItemCount()
    {
        return rewardsItems.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position))
        {
            return TYPE_HEADER;
        } else
        {
            return TYPE_ITEM;
        }
    }

    public boolean isPositionHeader(int position)
    {
        if (position == 0)
        {
            return true;
        } else
        {
            return false;
        }
    }


    class VHHeader extends RecyclerView.ViewHolder
    {
        TextView loyaltypoints;

        public VHHeader(View itemView)
        {
            super(itemView);
            this.loyaltypoints = (TextView) itemView.findViewById(R.id.loyaltypoints);
        }
    }

    class VHItem extends RecyclerView.ViewHolder
    {
        TextView gridName;
        TextView gridPoints;
        ImageView image;

        public VHItem(View itemView)
        {
            super(itemView);
            this.gridName = (TextView) itemView.findViewById(R.id.rewardsTitle);
            this.gridPoints = (TextView) itemView.findViewById(R.id.rewardsPoints);
            this.image = (ImageView) itemView.findViewById(R.id.rewardimage);

        }
    }
}

