package com.example.seo.festivalsendmessages.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.seo.festivalsendmessages.Activitys.MainActivity;
import com.example.seo.festivalsendmessages.Adapters.MessageHistoryBaseAdapter;
import com.example.seo.festivalsendmessages.Beans.MessageHistoryBean;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/3/7.
 */
public class MessagesHistoryFragment extends android.support.v4.app.Fragment {

    private ListView mHistoryListView;
    private MessageHistoryBaseAdapter adapter;
    private SQLiteDatabase database;
    private List<MessageHistoryBean> dataBeans = new ArrayList<MessageHistoryBean>();
    private View view;
    private boolean isCursorNull = false;
    private static final String TAG = "MessagesHistoryFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = MainActivity.database;
        initDatas();
        if(isCursorNull)
        {
            view = inflater.inflate(R.layout.nooneshow,container,false);
        }
        else
        {
            view = inflater.inflate(R.layout.messages_histroy,container,false);
            initViews();
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    private void initDatas()
    {
        Cursor cursor = database.query(DbOpenHelper.MessageHistroyTable,new String[]{DbOpenHelper.MessageHistroyTableColumns[0],DbOpenHelper.MessageHistroyTableColumns[1],DbOpenHelper.MessageHistroyTableColumns[2],DbOpenHelper.MessageHistroyTableColumns[3]},null,null,null,null,"_id");
        if(cursor!=null)
        {
            while(cursor.moveToNext()) {
                String phoneNumber = cursor.getString(cursor.getColumnIndex(DbOpenHelper.MessageHistroyTableColumns[0]));
                int messageId = cursor.getInt(cursor.getColumnIndex(DbOpenHelper.MessageHistroyTableColumns[1]));
                int festivalId = cursor.getInt(cursor.getColumnIndex(DbOpenHelper.MessageHistroyTableColumns[2]));
                String date = cursor.getString(cursor.getColumnIndex(DbOpenHelper.MessageHistroyTableColumns[3]));
                String message = null;
                String name = getContactName(phoneNumber);
                Cursor select = database.query(DbOpenHelper.FestivalMessagesTable, new String[]{DbOpenHelper.FestivalMessagesTableColumns[1]}, "_id = " + messageId, null, null, null, null);
                if(select.moveToFirst()) {
                    message = select.getString(select.getColumnIndex(DbOpenHelper.FestivalMessagesTableColumns[1]));
                }
                select.close();
                MessageHistoryBean bean = new MessageHistoryBean(phoneNumber,name,messageId,festivalId,date,message);
                dataBeans.add(bean);
            }
        }
        if(cursor.getCount() == 0) {
            isCursorNull = true;
        }
        cursor.close();
    }

    private String getContactName(String phoneNumber)
    {
        String name = null;
        Cursor contactId = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.NUMBER + "=?",new String[]{phoneNumber},null,null);
        if(contactId.moveToFirst())
        {
            name = contactId.getString(contactId.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        }
/*        Cursor contactId = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.NUMBER + "= ?",new String[]{phoneNumber},null);
        String name = contactId.getString(contactId.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));*/
//        int id = contactId.getInt(contactId.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
/*        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, ContactsContract.Contacts._ID + "=?",new String[]{new String(String.valueOf(id))}, null);
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));*/
        contactId.close();
//        cursor.close();
        return name;
    }

    private void initViews()
    {
        mHistoryListView = (ListView) view.findViewById(R.id.id_MessageHistory_List);
        adapter = new MessageHistoryBaseAdapter(getContext(),dataBeans,R.layout.history_item);
        mHistoryListView.setAdapter(adapter);
    }
}
