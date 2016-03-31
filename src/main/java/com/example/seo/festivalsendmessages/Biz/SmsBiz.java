package com.example.seo.festivalsendmessages.Biz;

import android.app.PendingIntent;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Seo on 2016/3/24.
 */
public class SmsBiz {

    public int sendMessage(String number,String content,PendingIntent sendPi,PendingIntent deliveryPi)
    {
        Log.i("Tag","11");
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> message = smsManager.divideMessage(content);
        for(String s : message)
        {
            smsManager.sendTextMessage(number,null,s,sendPi,deliveryPi);
        }
        return message.size();
    }

    public int sendMessages(Set<String> numbers,String content,PendingIntent sendPi,PendingIntent deliveryPi)
    {
        int result = 0;
        for(String s : numbers)
        {
            int count = sendMessage(s,content,sendPi,deliveryPi);
            result += count;
        }
        return result;
    }
}
