package com.refocus.puneet.axishackathon.Fragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.refocus.puneet.axishackathon.Activities.MainActivity;
import com.refocus.puneet.axishackathon.Adapters.HelpAdapter;
import com.refocus.puneet.axishackathon.Adapters.SummaryAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.HelpItem;
import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.R;
import com.refocus.puneet.axishackathon.RecyclerItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment
{

    HelpAdapter mAdapter;
    ArrayList<HelpItem> helpItems;
    AppManager manager;

    public static StatisticsFragment newInstance()
    {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    public StatisticsFragment()
    {
        // Required empty public constructor
    }
    Double restaurant, travel, recharge, withdraw, shopping, cinema;
    Double total;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        helpItems = new ArrayList<>();
        manager = (AppManager) getActivity().getApplication();
        restaurant = travel = recharge = withdraw = shopping = cinema = total = 0.0;
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Spending Statistics");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        for(int i = 0; i < manager.globalCurrentAccount.transactions.size(); i++)
        {
            Transaction transaction = manager.globalCurrentAccount.transactions.get(i);
            if (!transaction.credit)
            {
                Log.d("Statistics", "amount str " + transaction.amt);
                Log.d("Statistics", "amount lng" + Double.parseDouble(transaction.amt));
                if (transaction.type.startsWith("Rest"))
                {
                    restaurant += Double.parseDouble(transaction.amt);
                }
                else
                {
                    if (transaction.type.startsWith("Cin"))
                    {
                        cinema += Double.parseDouble(transaction.amt);
                    }
                    else
                    {
                        if (transaction.type.startsWith("With"))
                        {
                            withdraw += Double.parseDouble(transaction.amt);
                        }
                        else
                        {
                            if (transaction.type.startsWith("Shop"))
                            {
                                shopping += Double.parseDouble(transaction.amt);
                            }
                            else
                            {
                                if (transaction.type.startsWith("Rech"))
                                {
                                    recharge += Double.parseDouble(transaction.amt);
                                }
                                else
                                {
                                    if (transaction.type.startsWith("Trav"))
                                        travel += Double.parseDouble(transaction.amt);
                                    else
                                        total += Double.parseDouble(transaction.amt);
                                }
                            }
                        }
                    }
                }
            }
        }

        total = restaurant + travel + recharge + withdraw + shopping + cinema;
        Log.d("Statistics", "total " + total);
        Log.d("Statistics", "restaurant " + restaurant);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        LinearLayout ll1 = (LinearLayout) view.findViewById(R.id.backgroundLayout1);
        ll1.getLayoutParams().width = (int) ((restaurant/total) * width);
        ll1.requestLayout();
        TextView p1 = (TextView) view.findViewById(R.id.percentage1);
        p1.setText("" + (int) (restaurant * 100/total) + "%");

        LinearLayout ll2 = (LinearLayout) view.findViewById(R.id.backgroundLayout2);
        ll2.getLayoutParams().width = (int) ((cinema/total) * width);
        ll2.requestLayout();
        TextView p2 = (TextView) view.findViewById(R.id.percentage2);
        p2.setText("" + (int) (cinema * 100/total) + "%");

        LinearLayout ll3 = (LinearLayout) view.findViewById(R.id.backgroundLayout3);
        ll3.getLayoutParams().width = (int) ((recharge/total) * width);
        ll3.requestLayout();
        TextView p3 = (TextView) view.findViewById(R.id.percentage3);
        p3.setText("" + (int) (recharge * 100/total) + "%");

        LinearLayout ll4 = (LinearLayout) view.findViewById(R.id.backgroundLayout4);
        ll4.getLayoutParams().width = (int) ((withdraw/total) * width);
        ll4.requestLayout();
        TextView p4 = (TextView) view.findViewById(R.id.percentage4);
        p4.setText("" + (int) (withdraw * 100/total) + "%");

        LinearLayout ll5 = (LinearLayout) view.findViewById(R.id.backgroundLayout5);
        ll5.getLayoutParams().width = (int) ((shopping/total) * width);
        ll5.requestLayout();
        TextView p5 = (TextView) view.findViewById(R.id.percentage5);
        p5.setText("" + (int) (shopping * 100/total) + "%");

        LinearLayout ll6 = (LinearLayout) view.findViewById(R.id.backgroundLayout6);
        ll6.getLayoutParams().width = (int) ((travel/total) * width);
        ll6.requestLayout();
        TextView p6 = (TextView) view.findViewById(R.id.percentage6);
        p6.setText("" + (int) (travel * 100/total) + "%");

        TextView tvTotal = (TextView) view.findViewById(R.id.tv_total_spent);
        tvTotal.setText("Rs. " + ((total/100*100)));
    }
}
