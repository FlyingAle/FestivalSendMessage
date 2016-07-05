package com.example.seo.festivalsendmessages.Services;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.seo.festivalsendmessages.Beans.RegularlySendBean;
import com.example.seo.festivalsendmessages.Biz.SmsBiz;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/6/7.
 */
public class MessageRegularlySendService extends Service {

    private SQLiteDatabase database;
    private DbOpenHelper helper = new DbOpenHelper(this,"FestivalDataBase",null,1);
    private List<RegularlySendBean> sendBeanList;
    private SmsBiz smsBiz = new SmsBiz();
    private static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    private BroadcastReceiver mSendReceiver;
    private PendingIntent mSendPi;

    @Override
    public void onCreate() {
        sendBeanList = new ArrayList<>();
        database = helper.getWritableDatabase();
        Cursor cursor = database.query(DbOpenHelper.RegularlySendTable,null,DbOpenHelper.RegularlySendTableColumns[3]="?",new String[]{"0"},"_id",null,null);
        if(cursor.moveToFirst())
        {
            String dates = cursor.getString(cursor.getColumnIndex(DbOpenHelper.RegularlySendTableColumns[0]));
            String message = cursor.getString(cursor.getColumnIndex(DbOpenHelper.RegularlySendTableColumns[1]));
            String number = cursor.getString(cursor.getColumnIndex(DbOpenHelper.RegularlySendTableColumns[2]));
            RegularlySendBean bean = new RegularlySendBean(message,number,dates);
            sendBeanList.add(bean);
        }
        cursor.close();
        initReceivers();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SimpleDateFormat sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd");
        String    date    =    sDateFormat.format(new    java.util.Date());
        for(int i=0;i<sendBeanList.size();i++)
        {
            RegularlySendBean bean = sendBeanList.get(i);
            if(bean.getDate() == date)
            {
                SmsBiz smsBiz = new SmsBiz();
                smsBiz.sendMessage(bean.getNumber(),bean.getMessage(),mSendPi,null);
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
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

                            Toast.makeText(MessageRegularlySendService.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(MessageRegularlySendService.this, "短信发送失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this,0,sendIntent,0);
        registerReceiver(mSendReceiver, new IntentFilter(ACTION_SEND_MSG));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
