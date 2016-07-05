package com.example.seo.festivalsendmessages.Services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import com.example.seo.festivalsendmessages.Activitys.SendMessagesActivity;
import com.example.seo.festivalsendmessages.Beans.FestivalDateBean;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;

import java.util.Calendar;

/**
 * Created by Seo on 2016/6/5.
 */
public class FestivalRemindService extends Service {

    public DbOpenHelper helper = new DbOpenHelper(this,"FestivalDataBase",null,1);
    public  SQLiteDatabase database;
    private int notification_Id = 1;
    private Calendar calendar = Calendar.getInstance();
    private FestivalDateBean bean;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(!database.isOpen())
        {
            database = helper.getWritableDatabase();
        }
        String date = calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        Cursor cursor = database.query(DbOpenHelper.FestivalDatesTable,null,DbOpenHelper.FestivalDatesTableColumns[1] + "=?",new String[]{date},"_id",null,null);
        if(cursor.getCount() != 0)
        {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalDatesTableColumns[0]));
            String fDate = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalDatesTableColumns[1]));
            bean = new FestivalDateBean(id,name,fDate);
            cursor.close();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(bean != null)
        {
            send_Notification(bean.getFestivalDate(),bean.getFestivalName());
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @SuppressLint("NewApi")  public void send_Notification(String festivalDate,String festivalName)
    {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this,SendMessagesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("You have a Notification Message!");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle(festivalName + "到了，给你的朋友发条祝福的短信吧");
        builder.setContentText("点击这里发送预约短信");
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(notification_Id, notification);
    }
}
