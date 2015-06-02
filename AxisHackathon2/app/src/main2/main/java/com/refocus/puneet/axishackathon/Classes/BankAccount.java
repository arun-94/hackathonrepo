package com.refocus.puneet.axishackathon.Classes;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Puneet on 30-05-2015.
 */
public class BankAccount
{
    public String AccNo;
    public String CustomerID;
    public String CustomerName;
    public String CreditCardEnabled;
    public String Balance;
    public ArrayList<Transaction> transactions;

    public BankAccount(String Acn, String usId, String bal)
    {
        AccNo = Acn;
        CustomerID = usId;
        Balance = bal;
        transactions = new ArrayList<Transaction>();
    }

    public BankAccount(String Acn, String usId, String usName, String ccEn)
    {
        AccNo = Acn;
        CustomerID = usId;
        CustomerName = usName;
        CreditCardEnabled = ccEn;
        Balance = "0";
        transactions = new ArrayList<Transaction>();
        Log.d("Account", "Created new Bank Account with Ac no " + AccNo + " for user " + usName);
    }
}
