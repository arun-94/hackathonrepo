package com.refocus.puneet.axishackathon.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.refocus.puneet.axishackathon.Adapters.SummaryAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SummaryAdapter mAdapter;
    AppManager manager;
    ArrayList<Transaction> transactionList = new ArrayList<>();
    String[] paymentType = {"Restaurant", "Atm", "Shopping", "Cinema", "Travel"};
    String[] debitType = {"Salary", "Gift", "Bonus", "Deposit"};
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, String param2)
    {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static SummaryFragment newInstance()
    {
        SummaryFragment fragment = new SummaryFragment();
        return fragment;
    }

    public SummaryFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        transactionList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false);
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
        mAdapter = new SummaryAdapter(getActivity(), transactionList);
        mRecyclerView.setAdapter(mAdapter);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("requestDate", "2015-05-18");
        params.put("accountId", "ACT000000000001");

        ParseCloud.callFunctionInBackground("MiniStatement", params, new FunctionCallback<HashMap<String, Object>>()
        {
            public void done(HashMap<String, Object> result, ParseException e)
            {
                JSONObject resultJSON = new JSONObject(result);

                if (e == null)
                {
                    try
                    {
                        for (int i = 0; i < resultJSON.getJSONArray("transactions").length(); i++)
                        {
                            try
                            {
                                JSONArray temp = resultJSON.getJSONArray("transactions");
                                JSONArray transactionString = temp.getJSONArray(i);
                                String amount = transactionString.getString(transactionString.length() - 1);
                                String type = transactionString.getString(transactionString.length() - 12);
                                char type1 = type.charAt(type.length() - 1);
                                String date = transactionString.getString(0);
                                String name = transactionString.getString(13);

                                Random rand = new Random();

                                // nextInt is normally exclusive of the top value,
                                // so add 1 to make it inclusive
                                if(type1 == 'C'||type1 == 'c')
                                {
                                    int randomNum = rand.nextInt(debitType.length);
                                    mAdapter.addItem(new Transaction(name, date, debitType[randomNum], amount, true));
                                }
                                else
                                {
                                    int randomNum = rand.nextInt(paymentType.length);
                                    mAdapter.addItem(new Transaction(name, date, paymentType[randomNum], amount, false));
                                }
                                Log.d("result:- ", amount  + date + name + type1);
                            }
                            catch (JSONException e1)
                            {
                                e1.printStackTrace();
                            }
                        }
                        mAdapter = new SummaryAdapter(getActivity(), transactionList);
                    }
                    catch (JSONException e1)
                    {
                        e1.printStackTrace();
                    }
                }
                else
                {
                    Log.d("Lematize", "Lematize error : " + e);
                }
            }
        });


    }
}
