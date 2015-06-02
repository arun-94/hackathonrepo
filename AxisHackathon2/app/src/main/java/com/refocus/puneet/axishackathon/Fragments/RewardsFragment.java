package com.refocus.puneet.axishackathon.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.refocus.puneet.axishackathon.Activities.MainActivity;
import com.refocus.puneet.axishackathon.Adapters.RewardsAdapter;
import com.refocus.puneet.axishackathon.Classes.RewardsItem;
import com.refocus.puneet.axishackathon.R;
import com.refocus.puneet.axishackathon.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Random;

public class RewardsFragment extends Fragment
{

    RewardsAdapter mAdapter;
    ArrayList<RewardsItem> rewardItems;
    String[] rewardType = {"Restaurant", "Coffee", "Shopping", "Cinema", "Recharge", "Travel"};


    public static RewardsFragment newInstance()
    {
        return new RewardsFragment();
    }

    public RewardsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Random rand = new Random();

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Rewards");

        rewardItems = new ArrayList<>();
        rewardItems.add(new RewardsItem("CA01", "123",  rand.nextInt(rewardType.length)));
        rewardItems.add(new RewardsItem("CA02", "234", rand.nextInt(rewardType.length)));
        rewardItems.add(new RewardsItem("CA03", "152", rand.nextInt(rewardType.length)));
        rewardItems.add(new RewardsItem("CA04", "124",  rand.nextInt(rewardType.length)));
        rewardItems.add(new RewardsItem("CA05", "798",  rand.nextInt(rewardType.length)));
        rewardItems.add(new RewardsItem("CA06", "253",  rand.nextInt(rewardType.length)));




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rewards, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rewardsRecyler);
        mRecyclerView.setHasFixedSize(true);

        final GridLayoutManager llm = new GridLayoutManager(getActivity(), 3);

        mRecyclerView.setLayoutManager(llm);
        mAdapter = new RewardsAdapter(getActivity(), rewardItems);
        llm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.isPositionHeader(position) ? llm.getSpanCount() : 1;
            }
        });
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Toast.makeText(getActivity(), "Offer Redeemed", Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}
