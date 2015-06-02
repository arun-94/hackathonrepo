package com.refocus.puneet.axishackathon;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.refocus.puneet.axishackathon.Classes.BankAccount;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Puneet on 05-04-2015.
 */
public class AppManager extends Application
{

    public String lat, lng;
    public ArrayList<BankAccount> bankAccounts;
    public BankAccount globalCurrentAccount;

    @Override
    public void onCreate()
    {
        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "5CacqV3GaCWICl8LV19LXZ2G2JbzPmjlupfDKIte", "CMHWwAJzGatSsrZU9YKGDoZk2PDLnNpZSHCqCks2");

        bankAccounts = new ArrayList<BankAccount>();

        super.onCreate();
    }





}
