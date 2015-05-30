package com.refocus.puneet.axishackathon.Fragments;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.refocus.puneet.axishackathon.Adapters.ChatArrayAdapter;
import com.refocus.puneet.axishackathon.AppManager;
import com.refocus.puneet.axishackathon.Classes.ChatMessage;
import com.refocus.puneet.axishackathon.R;

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
    AppManager manager;

    public static ChatFragment newInstance()
    {
        return new ChatFragment();
    }

    public ChatFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        manager = (AppManager) getActivity().getApplication();
        super.onCreate(savedInstanceState);
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

        chatArrayAdapter.add(new ChatMessage(true, "Hi, I'm Axis Fox! How's it going? Let me know how I can be of assistance of you in any of your money matters!"));
    }

    private boolean sendChatMessage()
    {
        chatArrayAdapter.add(new ChatMessage(false, chatText.getText().toString()));
        ProcessChat(chatText.getText().toString());
        chatText.setText("");
        return true;
    }

    public String ProcessChat(String input)
    {
        final String responseMessage = "";
        HashMap<String, Object> params = new HashMap<String, Object>();
        /*JSONArray mArr = new JSONArray();
        mArr.put("stores");
        mArr.put("running");*/
        params.put("inputString", input);
        ParseCloud.callFunctionInBackground("ProcessChat", params, new FunctionCallback<ArrayList<Integer>>()
        {
            public void done(ArrayList<Integer> result, ParseException e)
            {
                if (e == null)
                {
                    Log.d("Chat", "Result is  : " + result);

                    for (int i = 0; i < result.size(); i++)
                    {
                        Log.d("Chat", "" + result.get(i));
                    }

                    switch (result.get(0))
                    {
                        case 0:
                            chatArrayAdapter.add(new ChatMessage(true, "Sorry, I couldn't understand what you meant. Could you please try asking me something else?"));
                            break;
                        case 1:
                            HashMap<String, Object> params = new HashMap<String, Object>();
                            params.put("requestDate", "2015-05-18");
                            params.put("accountId", "ACT000000000001");
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
