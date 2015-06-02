package com.refocus.puneet.axishackathon.Fragments;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.refocus.puneet.axishackathon.Activities.MainActivity;
import com.refocus.puneet.axishackathon.Adapters.ChatArrayAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.ChatMessage;
import com.refocus.puneet.axishackathon.Classes.HelpItem;
import com.refocus.puneet.axishackathon.Classes.Transaction;
import com.refocus.puneet.axishackathon.GPSTracker;
import com.refocus.puneet.axishackathon.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatFragment extends Fragment
{
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private ImageButton buttonSend;
    boolean showGPSDialog = false;
    String lat;
    String lng;
    boolean emptytext = false;
    HelpItem helpItem = new HelpItem();


    GPSTracker gpsTracker = new GPSTracker(getActivity());

    AppManager manager;


    public static ChatFragment newInstance()
    {
        return new ChatFragment();
    }


    public static ChatFragment newInstance(HelpItem item)
    {
        return new ChatFragment(item);
    }

    public ChatFragment(HelpItem item)
    {
        helpItem = item;
    }

    public  ChatFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        manager = (AppManager) getActivity().getApplication();
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Axis Chat");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        buttonSend = (ImageButton) view.findViewById(R.id.buttonSend);

        listView = (ListView) view.findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getActivity(), R.layout.chat_single_message);

        chatText = (EditText) view.findViewById(R.id.editText_chat);
        chatText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    return sendChatMessage();
                }
                return false;
            }
        });

        buttonSend.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                switch (event.getAction())
                {

                    case MotionEvent.ACTION_DOWN:
                    {
                        buttonSend.setImageResource(R.drawable.ic_chat_send_hover);
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        sendChatMessage();
                        buttonSend.setImageResource(R.drawable.ic_chat_send);
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:
                    {
                        buttonSend.setImageResource(R.drawable.ic_chat_send);
                        break;
                    }
                }
                return true;
            }
        });
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver()
        {
            @Override
            public void onChanged()
            {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        if (helpItem.title == null)
        {
            chatArrayAdapter.add(new ChatMessage(true, "Hi, I'm Axis Fox! How's it going? Let me know how I can be of assistance of you in any of your money matters!"));
            chatArrayAdapter.add(new ChatMessage(true, "To know what all I can help you with - tap the 'Help' button in the Action Bar!"));
        }
        else
        {
            helpItem.description = helpItem.description.substring(3);
            chatArrayAdapter.add(new ChatMessage(false, helpItem.description));
            ProcessChat(helpItem.description);
        }
    }

    private boolean sendChatMessage()
    {
        if (!chatText.getText().toString().equals(""))
        {
            chatArrayAdapter.add(new ChatMessage(false, chatText.getText().toString()));
            ProcessChat(chatText.getText().toString());
            chatText.setText("");
            return true;
        }
        emptytext = true;
        return false;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (showGPSDialog)
        {
            checkLocation();
        }
    }


    public boolean checkLocation()
    {
        Log.d("lat", "Check location called");
        gpsTracker = new GPSTracker(getActivity());
        if (gpsTracker.canGetLocation())
        {
            manager.lat = lat = "" + gpsTracker.getLatitude();
            manager.lng = lng = "" + gpsTracker.getLongitude();
            if (Double.parseDouble(lat) == 0.0 && Double.parseDouble(lng) == 0.0)
            {
                chatArrayAdapter.add(new ChatMessage(true, "I was unable to find your location!"));
            }
            else
            {
                chatArrayAdapter.add(new ChatMessage(true, "I was able to get your location!"));
            }
            return true;
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            if (!showGPSDialog)
            {
                gpsTracker.showSettingsAlert();
                showGPSDialog = true;
            }

            return false;
        }
    }

    public String ProcessChat(String input)
    {
        final String responseMessage = "";
        HashMap<String, Object> params = new HashMap<String, Object>();
        /*JSONArray mArr = new JSONArray();
        mArr.put("stores");
        mArr.put("running");*/
        params.put("inputString", input);
        ParseCloud.callFunctionInBackground("ProcessChat", params, new FunctionCallback<ArrayList<String>>()
        {
            public void done(ArrayList<String> result, ParseException e)
            {
                if (e == null)
                {

                    int res = Integer.parseInt(result.get(0));
                    switch (res)
                    {
                        case 0:
                            chatArrayAdapter.add(new ChatMessage(true, "Sorry, I couldn't understand what you meant. Could you please try asking me something else?"));
                            break;
                        case 1:
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("requestDate", "2015-05-18");
                            params.put("accountId", manager.globalCurrentAccount.AccNo);
                            ParseCloud.callFunctionInBackground("GetBalance", params, new FunctionCallback<HashMap<String, Object>>()
                            {
                                public void done(HashMap<String, Object> result, ParseException e)
                                {
                                    JSONObject resultJSON = new JSONObject(result);
                                    //0 - date, 1 - currency, 2 - status, 3 - balances
                                    if (e == null)
                                    {
                                        for (int i = 0; i < resultJSON.names().length(); i++)
                                        {
                                            try
                                            {
                                                Log.d("JSON", "key = " + resultJSON.names().getString(i) + " value = " + resultJSON.get(resultJSON.names().getString(i)));
                                            }
                                            catch (JSONException e1)
                                            {
                                                e1.printStackTrace();
                                            }
                                        }
                                        try
                                        {
                                            if (resultJSON.getString("status").equals("000"))
                                            {
                                                String message = "The balance available in your account is \r\n";
                                                for (int i = 0; i < resultJSON.getJSONArray("balances").length(); i++)
                                                {
                                                    if (resultJSON.getJSONArray("balances").getString(i).equals("Available Balance"))
                                                    {
                                                        String a = resultJSON.getJSONArray("balances").getString(i + 1).substring(1, resultJSON.getJSONArray("balances").getString(i + 1).length());
                                                        message += "Rs. " + Long.parseLong(a) + "/- ";
                                                    }
                                                }
                                                chatArrayAdapter.add(new ChatMessage(true, message));
                                            }
                                            else
                                            {
                                                chatArrayAdapter.add(new ChatMessage(true, "Unable to retrieve your balance. Maybe there's a net problem?"));
                                            }
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
                            break;
                        case 2:
                            if (result.size() > 1)
                            {
                                chatArrayAdapter.add(new ChatMessage(true, "Refilling amount of Rs. " + result.get(1) + " in your phone"));
                            }
                            else
                            {
                                chatArrayAdapter.add(new ChatMessage(true, "Please specify the amount to refill!"));
                            }
                            break;
                        case 3:
                            chatArrayAdapter.add(new ChatMessage(true, "Hello, how can I help you?"));
                            break;
                        case 4:
                            chatArrayAdapter.add(new ChatMessage(true, "Whatsup dude! Need any help?"));
                            break;
                        case 5:
                            chatArrayAdapter.add(new ChatMessage(true, "Namaste, kya mai aapki madad kar sakta hu?"));
                            break;
                        case 6:
                            if (checkLocation())
                            {

                                HashMap<String, Object> paramsAtm = new HashMap<String, Object>();
                                paramsAtm.put("lat", lat);
                                paramsAtm.put("lng", lng);
                                ParseCloud.callFunctionInBackground("GetAtms", paramsAtm, new FunctionCallback<String>()
                                {
                                    public void done(String result, ParseException e)
                                    {
                                        chatArrayAdapter.add(new ChatMessage(true, result));

                                    }
                                });
                            }
                            break;

                        case 7:
                            if (checkLocation())
                            {

                                HashMap<String, Object> paramsAtm = new HashMap<String, Object>();
                                paramsAtm.put("lat", lat);
                                paramsAtm.put("lng", lng);
                                ParseCloud.callFunctionInBackground("GetRestaurants", paramsAtm, new FunctionCallback<String>()
                                {
                                    public void done(String result, ParseException e)
                                    {
                                        chatArrayAdapter.add(new ChatMessage(true, result));

                                    }
                                });
                            }
                            break;
                        case 8:
                            chatArrayAdapter.add(new ChatMessage(true, "Your branch is at Koregaon Park, Pune"));
                            break;
                        case 9:
                            if (checkLocation())
                            {

                                HashMap<String, Object> paramsAtm = new HashMap<String, Object>();
                                paramsAtm.put("lat", lat);
                                paramsAtm.put("lng", lng);
                                ParseCloud.callFunctionInBackground("GetBranch", paramsAtm, new FunctionCallback<String>()
                                {
                                    public void done(String result, ParseException e)
                                    {
                                        chatArrayAdapter.add(new ChatMessage(true, result));

                                    }
                                });
                            }
                            break;
                        case 10:
                            chatArrayAdapter.add(new ChatMessage(true, "Your debit card has been blocked"));
                            break;
                        case 11:
                            if (manager.globalCurrentAccount.CreditCardEnabled.equals("Y"))
                            {
                                chatArrayAdapter.add(new ChatMessage(true, "Your credit card has been blocked"));
                            }
                            else
                            {
                                chatArrayAdapter.add(new ChatMessage(true, "Your dont have a credit card. Please Apply For 1"));
                            }
                            break;
                        case 12:
                            chatArrayAdapter.add(new ChatMessage(true, "What do you want to block?"));
                            break;
                        case 13:
                            HashMap<String, Object> paramsAtm = new HashMap<String, Object>();
                            paramsAtm.put("customerId", "CUST00606");
                            paramsAtm.put("billId", "VODAFONE");
                            ParseCloud.callFunctionInBackground("InquireBill", paramsAtm, new FunctionCallback<ArrayList<String>>()
                            {
                                public void done(ArrayList<String> result, ParseException e)
                                {
                                    if (result.get(1).equals("Success"))
                                    {
                                        String message = "The ";
                                        message += result.get(3) + " bill is " + result.get(4) + " ,\r\n" + result.get(7) + " to be paid by " + result.get(6);
                                        chatArrayAdapter.add(new ChatMessage(true, message));
                                    }
                                    else
                                    {
                                        chatArrayAdapter.add(new ChatMessage(true, "Bill Inquiry Failed"));
                                    }
                                }
                            });
                            break;
                        case 14:
                            chatArrayAdapter.add(new ChatMessage(true, "Which bills are you looking for?"));
                            break;
                        case 15:
                            HashMap<String, Object> paramsPay = new HashMap<String, Object>();
                            paramsPay.put("customerId", "CUST00606");
                            paramsPay.put("billId", "VODAFONE");
                            ParseCloud.callFunctionInBackground("InquireBill", paramsPay, new FunctionCallback<ArrayList<String>>()
                            {
                                public void done(ArrayList<String> result, ParseException e)
                                {
                                    if (result.get(1).equals("Success"))
                                    {
                                        String message = "Your ";
                                        message += result.get(3) + " bill is " + result.get(4) + " \r\nINR" + result.get(7) + " will be paid soon from your account";
                                        chatArrayAdapter.add(new ChatMessage(true, message));
                                    }
                                    else
                                    {
                                        chatArrayAdapter.add(new ChatMessage(true, "Bill Inquiry Failed"));
                                    }
                                }
                            });
                            break;
                        case 16:
                            String inputString = result.get(1);
                            HashMap<String, Object> paramsStock = new HashMap<String, Object>();
                            paramsStock.put("inputString", inputString);
                            ParseCloud.callFunctionInBackground("GetStocks", paramsStock, new FunctionCallback<String>()
                            {
                                public void done(String result, ParseException e)
                                {

                                    chatArrayAdapter.add(new ChatMessage(true, result));

                                }
                            });
                            break;
                        case 17:
                            chatArrayAdapter.add(new ChatMessage(true, "Your Email Statement has been sent on EMAIL0001"));
                            break;
                        case 18:
                            chatArrayAdapter.add(new ChatMessage(true, "Your Request has been received. It will be processed soon"));
                            break;
                        case 19:
                            chatArrayAdapter.add(new ChatMessage(true, "Interest Rates\n" +
                                    "Home Loans : 9.85\n" +
                                    "Savings Account: 4%\n" +
                                    "Tax Saver Fixed Deposit: 8.25\n" +
                                    "Domestic Deposit: 8.40 for 1year < 13 Months"));
                            break;
                        case 20:
                            chatArrayAdapter.add(new ChatMessage(true, "Money Transfer Successful"));
                            break;
                        case 21:
                            chatArrayAdapter.add(new ChatMessage(true, "Account/Amount Not Specified"));
                            break;
                        case 22:
                            chatArrayAdapter.add(new ChatMessage(true, "Request For Money Transfer Successful"));
                            break;
                        case 23:
                            chatArrayAdapter.add(new ChatMessage(true, "A Representative from the bank will soon contact you"));
                            break;
                        case 24:
                            chatArrayAdapter.add(new ChatMessage(true, manager.globalCurrentAccount.transactions.get(0).type + " ," + manager.globalCurrentAccount.transactions.get(0).amt + " \r\n" + manager.globalCurrentAccount.transactions.get(0).name));
                            break;
                        case 25:
                            ArrayList<Transaction> transactions =  manager.globalCurrentAccount.transactions;
                            long sum = 0;
                            for( int  i = 0; i < transactions.size(); i++) {

                                if(transactions.get(i).credit) {
                                    String a = transactions.get(i).amt;
                                    Log.d("test", a);
                                    sum += Float.parseFloat(a);
                                }
                            }
                            chatArrayAdapter.add(new ChatMessage(true, "Total amount credited in your account is " + sum));
                            break;
                        case 26:
                            ArrayList<Transaction> transactions1 =  manager.globalCurrentAccount.transactions;
                            long sum1 = 0;
                            for( int  i = 0; i < transactions1.size(); i++) {

                                if(!transactions1.get(i).credit) {
                                    String a = transactions1.get(i).amt;
                                    Log.d("test", a);
                                    sum1 += Float.parseFloat(a);
                                }
                            }
                            chatArrayAdapter.add(new ChatMessage(true, "Total amount debited in your account is " + sum1));
                            break;
                        case 27:
                            HashMap<String, Object> paramPoint = new HashMap<String, Object>();
                            paramPoint.put("customerId", manager.globalCurrentAccount.CustomerID);
                            ParseCloud.callFunctionInBackground("ViewPoints", paramPoint, new FunctionCallback<ArrayList<String>>()
                            {
                                public void done(ArrayList<String> result, ParseException e)
                                {
                                    if(result != null)
                                    {
                                        Log.d("result1", result.get(0));
                                        chatArrayAdapter.add(new ChatMessage(true, "Your total loyalty is " + result.get(result.size() - 1)));
                                    }else {
                                        chatArrayAdapter.add(new ChatMessage(true, "I'm sorry, you Have no Loyalty Points"));
                                    }
                                }
                            });
                            break;
                        case 28:
                            HashMap<String, Object> paramCat = new HashMap<String, Object>();
                            paramCat.put("name", "C01");
                            ParseCloud.callFunctionInBackground("ViewCatalogue", paramCat, new FunctionCallback<HashMap<String, Object>>()
                            {
                                public void done(HashMap<String, Object> result, ParseException e)
                                {

                                    JSONObject resultJSON = new JSONObject(result);

                                    try
                                    {
                                        if(resultJSON.getString("description").equals("Success")) {
                                            JSONArray catarray = resultJSON.getJSONArray("catalogue");
                                            String message = "Your Catalogues is \n";
                                            for(int i  = 0; i < catarray.length(); i++) {
                                                String product = catarray.getJSONObject(i).getString("product");
                                                String points = catarray.getJSONObject(i).getString("points");
                                                message += "Product:- " + product + " Points:- " + points + "\r\n";
                                            }
                                            chatArrayAdapter.add(new ChatMessage(true, message));
                                        }
                                        else {
                                            chatArrayAdapter.add(new ChatMessage(true, "transaction failed"));
                                        }
                                    }
                                    catch (JSONException e1)
                                    {
                                        e1.printStackTrace();
                                    }

                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
                else
                {
                    Log.d("Char", "Chat error : " + e);
                }
            }
        });
        return responseMessage;
    }

}
