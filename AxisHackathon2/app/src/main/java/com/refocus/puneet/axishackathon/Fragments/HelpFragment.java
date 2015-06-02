package com.refocus.puneet.axishackathon.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.refocus.puneet.axishackathon.Activities.MainActivity;
import com.refocus.puneet.axishackathon.Adapters.HelpAdapter;
import com.refocus.puneet.axishackathon.Adapters.SummaryAdapter;
import com.refocus.puneet.axishackathon.Classes.HelpItem;
import com.refocus.puneet.axishackathon.R;
import com.refocus.puneet.axishackathon.RecyclerItemClickListener;

import java.util.ArrayList;

public class HelpFragment extends Fragment
{

    HelpAdapter mAdapter;
    ArrayList<HelpItem> helpItems;

    public static HelpFragment newInstance()
    {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    public HelpFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helpItems = new ArrayList<>();
        helpItems.add(new HelpItem("",""));
        helpItems.add(new HelpItem("Get Account Balance","eg. What is my current balance?"));
        helpItems.add(new HelpItem("Recharge Phone","eg. Recharge my phone for 100 bucks"));
        helpItems.add(new HelpItem("Get Outstanding Bill","eg. What is my outstanding or remaining bills?"));
        helpItems.add(new HelpItem("Pay Bills","eg. Pay my bills!"));
        helpItems.add(new HelpItem("Get nearest ATM","eg. Where is the neareast atm?"));
        helpItems.add(new HelpItem("Find Nearby Restaurants","eg. Are there any nearby restaurants?"));
        helpItems.add(new HelpItem("Check Stock Reports","eg. What is the stock status for GOOG"));
        helpItems.add(new HelpItem("Emails Bank Statement","eg. Email me my bank statement"));
        helpItems.add(new HelpItem("Block Credit or Debit Card","eg. Block my Credit Card"));
        helpItems.add(new HelpItem("Unblock Credit or Debit Card","eg. Unblock my Debit Card"));
        helpItems.add(new HelpItem("Check Interest Rates","eg. What are the interest rates for Axis?"));
        helpItems.add(new HelpItem("Transfer money","eg. Transfer 1000 to ACT000000021"));
        helpItems.add(new HelpItem("Request Money","eg. Request Money from ACT00000021"));
        helpItems.add(new HelpItem("Locate your Branch","eg. Where is my Branch?"));
        helpItems.add(new HelpItem("Request Tech Support from Bank","eg. Can you ask someone to contact me from Bank?"));
        helpItems.add(new HelpItem("Last Transaction","eg. What was my last transaction?"));
        helpItems.add(new HelpItem("Check Credit","eg. What is the total amount credit to my account?"));
        helpItems.add(new HelpItem("Check Debit","eg. What is the total amount debit from my account?"));
        helpItems.add(new HelpItem("Check Loyalty Points","eg. How much loyalty points do i own?"));
        helpItems.add(new HelpItem("Check Catalog","eg. Whats there in the catalogue"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Chat Help");

        return inflater.inflate(R.layout.fragment_help, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.summaryRecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new HelpAdapter(getActivity(), helpItems);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new ChatFragment(helpItems.get(position)), "ChatFragment")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack("HelpFragment")
                                .commit();
                    }
                })
        );
    }
}
