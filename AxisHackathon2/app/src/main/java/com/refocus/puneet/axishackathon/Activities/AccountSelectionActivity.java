package com.refocus.puneet.axishackathon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.refocus.puneet.axishackathon.Adapters.AccountSelectionAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.BankAccount;
import com.refocus.puneet.axishackathon.R;
import com.refocus.puneet.axishackathon.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class AccountSelectionActivity extends ActionBarActivity
{


    AccountSelectionAdapter mAdapter;
    AppManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        manager = (AppManager) getApplication();
        setContentView(R.layout.activity_account_selection);
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.AccountSelectionRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        Log.d("ONCREATE WAS CALLED", ""+1);
        final Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setTitle("Select Account");
        actionBar.canShowOverflowMenu();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AccountSelectionActivity.this, new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Intent listIntent = new Intent(AccountSelectionActivity.this, MainActivity.class);
                        listIntent.putExtra("Position", position);
                        listIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(listIntent);
                    }
                })
        );

        if(manager.bankAccounts.size() == 0)
        {
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("loginId", "" + "LOGINID0001");
            params.put("passCode", "" + "PWD0001");
            ParseCloud.callFunctionInBackground("VerifyUserLogin", params, new FunctionCallback<HashMap<String, Object>>()
            {
                public void done(HashMap<String, Object> result, ParseException e)
                {
                    JSONObject resultJSON = new JSONObject(result);
                    if (e == null)
                    {
                        try
                        {
                            if (resultJSON.getString("loginsuccess").equals("LOGIN SUCCESSFUL"))
                            {
                                JSONArray accounts = resultJSON.getJSONArray("accounts");
                                for (int j = 0; j < accounts.length(); j++)
                                {
                                    JSONObject thisAccount = accounts.getJSONObject(j);
                                    String accNo = thisAccount.getString("accountNumber");

                                    int flag = 0;
                                    for (int k = 0; k < manager.bankAccounts.size(); k++)
                                    {
                                        if (manager.bankAccounts.get(k).AccNo.equals(accNo))
                                        {
                                            flag = 1;
                                            break;
                                        }
                                    }
                                    if (flag == 0)
                                    {
                                        String ccEn = thisAccount.getString("creditCardEnabled");
                                        String custName = thisAccount.getString("customerName");
                                        String custNo = thisAccount.getString("customerNo");
                                        BankAccount newAcc = new BankAccount(accNo, custNo, custName, ccEn);
                                        manager.bankAccounts.add(newAcc);
                                    }
                                }
                            }

                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("requestDate", "2015-05-18");
                            JSONArray mAccNos = new JSONArray();
                            for (int i = 0; i < manager.bankAccounts.size(); i++)
                            {
                                mAccNos.put(manager.bankAccounts.get(i).AccNo);
                            }
                            params.put("accountId", mAccNos);
                            ParseCloud.callFunctionInBackground("MultiAccntBal", params, new FunctionCallback<HashMap<String, Object>>()
                            {
                                public void done(HashMap<String, Object> result, ParseException e)
                                {
                                    //mProgressBar.setVisibility(View.GONE);
                                    //mLoginButton.setVisibility(View.GONE);
                                    if (e == null)
                                    {
                                        JSONObject resultJSON = new JSONObject(result);
                                        try
                                        {
                                            JSONArray accountJSON = resultJSON.getJSONArray("accounts");
                                            for (int i = 0; i < accountJSON.length(); i++)
                                            {
                                                String a = accountJSON.getJSONObject(i).getJSONArray("balance").getString(3).substring(1, accountJSON.getJSONObject(i).getJSONArray("balance").getString(3).length());
                                                manager.bankAccounts.get(i).Balance = "Rs. " + Long.parseLong(a);
                                            }
                                            mAdapter = new AccountSelectionAdapter(AccountSelectionActivity.this, manager.bankAccounts);
                                            mRecyclerView.setAdapter(mAdapter);
                                        }
                                        catch (JSONException e1)
                                        {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                        catch (JSONException e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.d("ParseError", "" + e);
                    }
                }
            });
        }
        else
        {
            mAdapter = new AccountSelectionAdapter(this, manager.bankAccounts);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_account_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
