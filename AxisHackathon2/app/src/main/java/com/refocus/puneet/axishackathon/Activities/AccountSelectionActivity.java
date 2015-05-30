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

import com.refocus.puneet.axishackathon.Adapters.AccountSelectionAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.R;
import com.refocus.puneet.axishackathon.RecyclerItemClickListener;


public class AccountSelectionActivity extends ActionBarActivity
{


    AccountSelectionAdapter mAdapter;
    AppManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_selection);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.AccountSelectionRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new AccountSelectionAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        final Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setTitle("Select Account");
        actionBar.setNavigationIcon(R.drawable.ic_chatimage_fox);
        actionBar.canShowOverflowMenu();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(AccountSelectionActivity.this, new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Log.d("CLICKED", "Clicked on item");
                        if (position < 2)
                        {
                            Intent listIntent = new Intent(AccountSelectionActivity.this, MainActivity.class);
                            startActivity(listIntent);
                        }
                    }
                })
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_selection, menu);
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
