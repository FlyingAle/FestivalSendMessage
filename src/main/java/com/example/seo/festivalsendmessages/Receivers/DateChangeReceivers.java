package com.example.seo.festivalsendmessages.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.seo.festivalsendmessages.Services.MessageRegularlySendService;

/**
 * Created by Seo on 2016/6/12.
 */
public class DateChangeReceivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MessageRegularlySendService.class);
        context.startService(i);
    }
}
