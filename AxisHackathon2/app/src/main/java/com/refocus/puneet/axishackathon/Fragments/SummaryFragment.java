package com.refocus.puneet.axishackathon.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.refocus.puneet.axishackathon.Activities.MainActivity;
import com.refocus.puneet.axishackathon.Adapters.SummaryAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.BankAccount;
import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.R;
import com.refocus.puneet.axishackathon.RecyclerItemClickListener;

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
    String[] paymentType = {"Restaurant", "Withdrawal", "Shopping", "Cinema", "Recharge", "Travel"};
    String[] debitType = {"Salary", "Deposit"};
    static BankAccount currentAccount;
    int index;
    CircularProgressView progress;
    TextView yolo;
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

    public static SummaryFragment newInstance(BankAccount ba)
    {
        SummaryFragment fragment = new SummaryFragment();
        currentAccount = ba;
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
        manager = (AppManager) getActivity().getApplication();
        index = manager.bankAccounts.indexOf(currentAccount);
        transactionList = manager.bankAccounts.get(index).transactions;

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Account Summary");


        Log.d("index", "index is " + index);
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
        mAdapter = new SummaryAdapter(getActivity(), transactionList, currentAccount);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("size", ""+manager.bankAccounts.get(index).transactions.size());

        progress = (CircularProgressView) view.findViewById(R.id.progress_view_transaction);
        yolo = (TextView) view.findViewById(R.id.yolo);

        if (manager.bankAccounts.get(index).transactions.size() == 0)
        {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("requestDate", "2015-05-18");
            Log.d("Curr", currentAccount.AccNo);
            params.put("accountId", currentAccount.AccNo);

            ParseCloud.callFunctionInBackground("MiniStatement", params, new FunctionCallback<HashMap<String, Object>>()
            {
                public void done(HashMap<String, Object> result, ParseException e)
                {
                    if (result == null)
                    {
                        yolo.setText("No Transactions Found");
                        progress.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        JSONObject resultJSON = new JSONObject(result);
                        if (e == null)
                        {
                            try
                            {
                                Log.d("Number of transactions", resultJSON.getJSONArray("transactions").length()+"");
                                for (int i = 0; i < resultJSON.getJSONArray("transactions").length(); i++)
                                {
                                    try
                                    {
                                        JSONArray temp = resultJSON.getJSONArray("transactions");
                                        JSONArray transactionString = temp.getJSONArray(i);
                                        Log.d("test", "" + transactionString);
                                        ArrayList<String> parsedStrings = new ArrayList<String>();
                                        for (int j = 0; j < transactionString.length(); j++)
                                        {
                                            if (transactionString.getString(j).equals("") || transactionString.getString(j).equals(null))
                                            {
                                                //do nothing
                                            }
                                            else
                                            {
                                                parsedStrings.add(transactionString.getString(j));
                                            }
                                        }
                                        Log.d("parse", "" + parsedStrings);
                                        String amount = parsedStrings.get(parsedStrings.size() - 1);
                                        String type = parsedStrings.get(parsedStrings.size() - 2);
                                        char type1 = type.charAt(type.length() - 1);
                                        String date = parsedStrings.get(0);
                                        // date = new StringBuilder(date).reverse().toString();

                                        String year = date.substring(0, 4);
                                        String month = date.substring(4, 6);
                                        String[] monthNames = new String[12];
                                        monthNames[0] = "January, ";
                                        monthNames[1] = "February, ";
                                        monthNames[2] = "March, ";
                                        monthNames[3] = "April, ";
                                        monthNames[4] = "May, ";
                                        monthNames[5] = "June, ";
                                        monthNames[6] = "July, ";
                                        monthNames[7] = "August, ";
                                        monthNames[8] = "September, ";
                                        monthNames[9] = "October, ";
                                        monthNames[10] = "November, ";
                                        monthNames[11] = "December, ";

                                        String day = date.substring(6, 8);

                                        String date1 = day + " " + monthNames[Integer.parseInt(month) - 1] + "" + year;
                                        int count = 1;
                                        String name = "";
                                        while ((parsedStrings.get(count) != "D" || parsedStrings.get(count) != "C") && count < parsedStrings.size() - 1)
                                        {
                                            name += parsedStrings.get(count) + " ";
                                            count++;
                                        }
                                        Random rand = new Random();

                                        // nextInt is normally exclusive of the top value,
                                        // so add 1 to make it inclusive
                                        Log.d("TYPEREDITORDEBIT", "" + type1);
                                        if (type1 == 'C' || type1 == 'c')
                                        {
                                            int randomNum = rand.nextInt(debitType.length);
                                            Transaction t = new Transaction(name, date1, debitType[randomNum], amount, true);
                                            manager.bankAccounts.get(index).transactions.add(t);
                                            mAdapter.addItem(t);
                                        }
                                        else
                                        {
                                            int randomNum = rand.nextInt(paymentType.length);
                                            Transaction t = new Transaction(name, date1, paymentType[randomNum], amount, false);
                                            manager.bankAccounts.get(index).transactions.add(t);
                                            mAdapter.addItem(t);
                                        }
                                        Log.d("result:- ", amount + " Date:-" + date + " " + name + type1);
                                    }
                                    catch (JSONException e1)
                                    {
                                        e1.printStackTrace();
                                    }


                                }
                                progress.setVisibility(View.INVISIBLE);
                                yolo.setVisibility(View.INVISIBLE);
                                mAdapter = new SummaryAdapter(getActivity(), transactionList, currentAccount);
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
                }
            });

        }
        else
        {
            progress.setVisibility(View.INVISIBLE);
            yolo.setVisibility(View.INVISIBLE);
        }
    }


}
