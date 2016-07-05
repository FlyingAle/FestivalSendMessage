package com.example.seo.festivalsendmessages.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.seo.festivalsendmessages.Services.FestivalRemindService;

/**
 * Created by Seo on 2016/6/5.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, FestivalRemindService.class);
        Toast.makeText(context, "run", Toast.LENGTH_SHORT).show();
        context.startService(i);
    }
}
