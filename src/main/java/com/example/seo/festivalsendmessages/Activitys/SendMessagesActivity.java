package com.example.seo.festivalsendmessages.Activitys;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seo.festivalsendmessages.Adapters.ChooseMessageBaseAdapter;
import com.example.seo.festivalsendmessages.Beans.FestivalMessageBean;
import com.example.seo.festivalsendmessages.Biz.SmsBiz;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.view.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.HashSet;

/**
 * Created by Seo on 2016/3/23.
 */
public class SendMessagesActivity extends Activity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private FestivalMessageBean bean;
    private EditText m_Et_Message;
    private Button m_Btn_AddContact;
    private FloatingActionButton m_Fab_SendMessage;
    private FrameLayout m_FL_Loading;
    private FlowLayout m_Fl_Contacts;
    private int festivalId;
    private HashSet<String> mContactNames = new HashSet<String>();
    private HashSet<String> mContactNums = new HashSet<String>();
    private LayoutInflater inflater;
    private static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    private static final String ACTION_DELIVERY_MSG = "ACTION_DELIVERY_MSG";
    private BroadcastReceiver mSendReceiver;
    private BroadcastReceiver mDeliveryReceiver;
    private PendingIntent mSendPi;
    private PendingIntent mDeliveryPi;
    private int mMessgaeCount = 0;
    private int mSendCount = 0;
    private SQLiteDatabase database = MainActivity.database;
    private SmsBiz smsBiz = new SmsBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage_activity);
        inflater = LayoutInflater.from(this);
        initDatas();
        initViews();
        initEvents();
        initReceivers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendReceiver);
        unregisterReceiver(mDeliveryReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactNum = getPhoneNumber(cursor);
                if(!TextUtils.isEmpty(contactNum)) {
                    mContactNums.add(contactNum);
                    mContactNames.add(contactName);
                    addTags(contactName,contactNum);
                }
                cursor.close();
            }
        }
    }

    public String getPhoneNumber(Cursor cursor)
    {
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        if(numberCount > 0)
        {
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" +contactId,null,null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    private void initReceivers()
    {
        mSendReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(getResultCode() == RESULT_OK) {
                   // Toast.makeText(SendMessagesActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                    Log.i("Tag","短信发送成功");
                }
                else
                {
                   // Toast.makeText(SendMessagesActivity.this, "短信发送失败", Toast.LENGTH_SHORT).show();
                }
                mSendCount ++;
                if(mSendCount == mMessgaeCount)
                {
                    finish();
                }
            }
        };
        mDeliveryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(SendMessagesActivity.this,"对方已接受短信息",Toast.LENGTH_SHORT).show();
            }
        };
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this,0,sendIntent,0);
        Intent deliveryIntent = new Intent(ACTION_DELIVERY_MSG);
        mDeliveryPi = PendingIntent.getBroadcast(this,0,deliveryIntent,0);
        registerReceiver(mSendReceiver, new IntentFilter());
        registerReceiver(mDeliveryReceiver, new IntentFilter());
    }

    private void initEvents() {
        m_Btn_AddContact.setOnClickListener(this);
        m_Fab_SendMessage.setOnClickListener(this);
    }

    private void initViews() {
        m_Et_Message = (EditText) findViewById(R.id.id_Et_message);
        m_Btn_AddContact = (Button) findViewById(R.id.id_Bt_addcontact);
        m_Fab_SendMessage = (FloatingActionButton) findViewById(R.id.id_Fab_sendMessage);
        m_FL_Loading = (FrameLayout) findViewById(R.id.id_FL_loading);
        m_Fl_Contacts = (FlowLayout) findViewById(R.id.id_Fl_contacts);
        if(festivalId == -1)
        {
            m_Et_Message.setText(bean.getMessage());
        }
    }

    private void initDatas() {
        festivalId = getIntent().getIntExtra(ChooseMessagesActivity.KEY_NOMODELMESSAGE,-1);
        if(festivalId == -1) {
            Bundle bundle = getIntent().getBundleExtra(ChooseMessageBaseAdapter.INTENTKEY);
            bean = (FestivalMessageBean) bundle.getSerializable(ChooseMessageBaseAdapter.BUNDLEKEY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_Bt_addcontact:
                Intent intent  = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.id_Fab_sendMessage:
                m_FL_Loading.setVisibility(View.VISIBLE);
                String msg = m_Et_Message.getText().toString().trim();
                if(mContactNums.size() == 0)
                {
                    Toast.makeText(SendMessagesActivity.this,"请选择联系人",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(msg.isEmpty())
                {
                    Toast.makeText(SendMessagesActivity.this,"无法发送空短信",Toast.LENGTH_SHORT).show();
                    return;
                }
                mMessgaeCount = smsBiz.sendMessages(mContactNums,msg,mSendPi,mDeliveryPi);
                saveSendHistory();
                mSendCount = 0;
                m_FL_Loading.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void saveSendHistory()
    {
        int historyFestivalID = bean.getFestivalId();
        int historyMessageId = bean.getMessageId();
        SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
        String    date    =    sDateFormat.format(new    java.util.Date());
        for(String s : mContactNums)
        {
            database.execSQL("insert into " + DbOpenHelper.MessageHistroyTable + "(" + DbOpenHelper.MessageHistroyTableColumns[0] + "," + DbOpenHelper.MessageHistroyTableColumns[1] + "," + DbOpenHelper.MessageHistroyTableColumns[2] + ","+DbOpenHelper.MessageHistroyTableColumns[3]+") values('" + s + "'," + historyMessageId + "," + historyFestivalID + ",'"+date+"')");
        }
    }

    public void addTags(String contactName,String contactNum)
    {
        TextView textView = (TextView) inflater.inflate(R.layout.item_textview,m_Fl_Contacts,false);
        textView.setText(contactName);
        textView.setTag(contactNum);
        m_Fl_Contacts.addView(textView);
    }
}
