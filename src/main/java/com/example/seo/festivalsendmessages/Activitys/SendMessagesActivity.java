package com.example.seo.festivalsendmessages.Activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seo.festivalsendmessages.Adapters.ChooseMessageBaseAdapter;
import com.example.seo.festivalsendmessages.Beans.FestivalMessageBean;
import com.example.seo.festivalsendmessages.Biz.SmsBiz;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.view.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by Seo on 2016/3/23.
 */
public class SendMessagesActivity extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private static final int REQUEST_CODE = 1;
    private FestivalMessageBean bean;
    private EditText m_Et_Message;
    private Button m_Btn_AddContact;
    private FloatingActionButton m_Fab_SendMessage;
    private FlowLayout m_Fl_Contacts;
    private CheckBox m_Cb_send ;
    private TextView m_Tv_SendDate;
    private int festivalId;
    private HashSet<String> mContactNames = new HashSet<String>();
    private HashSet<String> mContactNums = new HashSet<String>();
    private LayoutInflater inflater;
    private static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    private BroadcastReceiver mSendReceiver;
    private PendingIntent mSendPi;
    private int mMessgaeCount = 0;
    private int mSendCount = 0;
    private SQLiteDatabase database = MainActivity.database;
    private SmsBiz smsBiz = new SmsBiz();
    private String regularlyDate;
    private boolean isNoModel = true;
    private int historyFestivalID;
    private int historyMessageId;

    private static final String TAG = "SendMessagesActivity";

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
                if(intent.getAction().equals(ACTION_SEND_MSG))
                {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:

                            Toast.makeText(SendMessagesActivity.this, mMessgaeCount+"条短信发送成功", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(SendMessagesActivity.this, "短信发送失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this,0,sendIntent,0);
        registerReceiver(mSendReceiver, new IntentFilter(ACTION_SEND_MSG));
    }

    private void initEvents() {
        m_Btn_AddContact.setOnClickListener(this);
        m_Fab_SendMessage.setOnClickListener(this);
    }



    private void initViews() {
        m_Et_Message = (EditText) findViewById(R.id.id_Et_message);
        m_Btn_AddContact = (Button) findViewById(R.id.id_Bt_addcontact);
        m_Fab_SendMessage = (FloatingActionButton) findViewById(R.id.id_Fab_sendMessage);
        m_Fl_Contacts = (FlowLayout) findViewById(R.id.id_Fl_contacts);
        m_Cb_send = (CheckBox) findViewById(R.id.subscribeSend);
        m_Cb_send.setOnCheckedChangeListener(this);
        m_Tv_SendDate = (TextView) findViewById(R.id.textView);
        if(!isNoModel)
        {
            m_Et_Message.setText(bean.getMessage());
        }
    }

    private void initDatas() {
        isNoModel = getIntent().getBooleanExtra(ChooseMessagesActivity.KEY_NOMODELMESSAGE,true);
        if(isNoModel) {
            festivalId = getIntent().getIntExtra(ChooseMessagesActivity.FESTIVAL_ID, -1);
        }
        else {
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
                if(m_Cb_send.isChecked())
                {
                    ContentValues values = new ContentValues();
                    values.put(DbOpenHelper.RegularlySendTableColumns[0],regularlyDate);
                    values.put(DbOpenHelper.RegularlySendTableColumns[1],msg);
                    values.put(DbOpenHelper.RegularlySendTableColumns[2], String.valueOf(mContactNums));
                    values.put(DbOpenHelper.RegularlySendTableColumns[3],false);
                    database.insert(DbOpenHelper.RegularlySendTable,null,values);
                    finish();
                }
                if(isNoModel)
                {
                    saveSendModel();
                }
                saveSendHistory();
                mMessgaeCount = smsBiz.sendMessages(mContactNums,msg,mSendPi,null);
                mSendCount = 0;
                break;
        }
    }

    public void saveSendHistory()
    {
        if(!isNoModel) {
            historyFestivalID = bean.getFestivalId();
            historyMessageId = bean.getMessageId();
        }
        else
        {
            historyFestivalID = festivalId;
        }
        SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd HH:mm");
        String    date    =    sDateFormat.format(new    java.util.Date());
        for(String s : mContactNums)
        {
            Log.i(TAG, "saveSendHistory: " + "insert into " + DbOpenHelper.MessageHistroyTable + "(" + DbOpenHelper.MessageHistroyTableColumns[0] + "," + DbOpenHelper.MessageHistroyTableColumns[1] + "," + DbOpenHelper.MessageHistroyTableColumns[2] + ","+DbOpenHelper.MessageHistroyTableColumns[3]+") values('" + s + "'," + historyMessageId + "," + historyFestivalID + ",'"+date+"')");
            database.execSQL("insert into " + DbOpenHelper.MessageHistroyTable + "(" + DbOpenHelper.MessageHistroyTableColumns[0] + "," + DbOpenHelper.MessageHistroyTableColumns[1] + "," + DbOpenHelper.MessageHistroyTableColumns[2] + ","+DbOpenHelper.MessageHistroyTableColumns[3]+") values('" + s + "'," + historyMessageId + "," + historyFestivalID + ",'"+date+"')");
/*            ContentValues values = new ContentValues();
            values.put(DbOpenHelper.MessageHistroyTableColumns[0],s);
            values.put(DbOpenHelper.MessageHistroyTableColumns[1],historyFestivalID);
            values.put(DbOpenHelper.MessageHistroyTableColumns[2],historyMessageId);
            values.put(DbOpenHelper.MessageHistroyTableColumns[3],date);
            database.insert(DbOpenHelper.MessageHistroyTable,null,values);*/
            database.close();
        }
    }

    public void saveSendModel()
    {
        String modelMessage = m_Et_Message.getText().toString().trim();
        Cursor c = database.query(DbOpenHelper.FestivalMessagesTable,null,DbOpenHelper.FestivalMessagesTableColumns[1] + "=?",new String[]{m_Et_Message.getText().toString().trim()},null,null,null);
        if(c.getCount() == 0) {
            int modelFestivalId;
            if (isNoModel) {
                modelFestivalId = festivalId;
            } else {
                modelFestivalId = bean.getFestivalId();
            }
            ContentValues values = new ContentValues();
            values.put(DbOpenHelper.FestivalMessagesTableColumns[0], modelFestivalId);
            values.put(DbOpenHelper.FestivalMessagesTableColumns[1], modelMessage);
            database.insert(DbOpenHelper.FestivalMessagesTable, null, values);
        }
        c.close();
        Cursor cursor = database.query(DbOpenHelper.FestivalMessagesTable, new String[]{"_id"}, DbOpenHelper.FestivalMessagesTableColumns[1] + "=?", new String[]{modelMessage}, null, null, null);
        if (cursor.moveToFirst()) {
            historyMessageId = cursor.getInt(cursor.getColumnIndex("_id"));
            cursor.close();
        }
    }

    public void addTags(String contactName,String contactNum)
    {
        TextView textView = (TextView) inflater.inflate(R.layout.item_textview,m_Fl_Contacts,false);
        textView.setText(contactName);
        textView.setTag(contactNum);
        m_Fl_Contacts.addView(textView);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int monthOfYear = calendar.get(Calendar.MONTH);
            int dayOfMaonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    regularlyDate = year +"-" +(monthOfYear+1) +"-"+ dayOfMonth;
                    m_Tv_SendDate.setText(regularlyDate+"");
                }
            },year,monthOfYear,dayOfMaonth);
            dialog.show();
        }
    }
}
