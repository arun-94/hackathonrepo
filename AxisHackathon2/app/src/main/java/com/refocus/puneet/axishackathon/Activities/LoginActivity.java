package com.refocus.puneet.axishackathon.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.BankAccount;
import com.refocus.puneet.axishackathon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends ActionBarActivity
{

    Button mLoginButton;
    EditText mUserNameEditText, mPasswordEditText;
    CircularProgressView mProgressBar;
    AppManager manager;
    boolean allowLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = (AppManager) getApplication();

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true))
        {

            Intent SearchIntent = new Intent(LoginActivity.this, TutorialActivity.class);
            SearchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(SearchIntent);
            settings.edit().putBoolean("my_first_time", false).commit();
        }
        else


        mLoginButton = (Button) findViewById(R.id.loginButton);
        mUserNameEditText = (EditText) findViewById(R.id.editText_custid);
        mPasswordEditText = (EditText) findViewById(R.id.editText_password);
        mProgressBar = (CircularProgressView) findViewById(R.id.progress_view);
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (allowLogin)
                {
                    if (mUserNameEditText.getText().toString().trim().length() == 0 || mPasswordEditText.getText().toString().trim().length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Do not leave any fields blank!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mProgressBar.startAnimation();
                        mLoginButton.setText("");
                        Log.d("Login", "Allowed login");
                        allowLogin = false;
                        HashMap<String, Object> params = new HashMap<String, Object>();
                        params.put("loginId", "" + mUserNameEditText.getText());
                        params.put("passCode", "" + mPasswordEditText.getText());
                        ParseCloud.callFunctionInBackground("VerifyUserLogin", params, new FunctionCallback<HashMap<String, Object>>()
                        {
                            public void done(HashMap<String, Object> result, ParseException e)
                            {
                                allowLogin = true;
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
                                                if(flag == 0)
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
                                                mProgressBar.setVisibility(View.GONE);
                                                mLoginButton.setText("Login");
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
                                                    }
                                                    catch (JSONException e1)
                                                    {
                                                        e1.printStackTrace();
                                                    }
                                                    Intent SearchIntent = new Intent(LoginActivity.this, AccountSelectionActivity.class);
                                                    SearchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(SearchIntent);
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
            }
        }
    }

    );
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
