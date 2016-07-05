package com.example.seo.festivalsendmessages.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.example.seo.festivalsendmessages.Receivers.DateChangeReceivers;

/**
 * Created by Seo on 2016/6/12.
 */
public class TimerServices extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DateChangeReceivers receivers = new DateChangeReceivers();
        registerReceiver(receivers,new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
