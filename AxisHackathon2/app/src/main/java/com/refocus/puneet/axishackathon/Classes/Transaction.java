package com.refocus.puneet.axishackathon.Classes;

import android.widget.ImageView;

/**
 * Created by Puneet on 30-05-2015.
 */
public class Transaction
{
    public String name;
    public String date;
    public String type;
    public String amt;
    public boolean real;
    public boolean credit;

    public Transaction(String n, String d, String t, String a, boolean credit)
    {
        name = n;
        date = d;
        type = t;
        amt = a;
        real = true;
        this.credit = credit;
    }

    public Transaction()
    {
        real = false;
    }
}
