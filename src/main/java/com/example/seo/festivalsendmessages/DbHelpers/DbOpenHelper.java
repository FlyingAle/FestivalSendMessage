package com.example.seo.festivalsendmessages.DbHelpers;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seo on 2016/3/7.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private String[] festival_Names = {"愚人节","劳动节","国庆节","元旦","儿童节"};
    private String[] festival_Dates = {"04-01","05-01","10-01","01-01","06-01"};
    public static final String FestivalDatesTable = "FestivalDates";
    public static final String FestivalMessagesTable = "FestivalMessages";
    public static final String MessageHistroyTable = "MessageHistroy";
    public static final String[] FestivalDatesTableColumns = {"festivalName","festivalDate"};
    public static final String[] FestivalMessagesTableColumns = {"festivalId","messageText"};
    public static final String[] MessageHistroyTableColumns = {"acceptanceNumber","messageId","festivalId","sendDate"};

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+FestivalDatesTable+
                "(" +
                "_id integer primary key autoincrement," +
                FestivalDatesTableColumns[0]+" text not null," +
                FestivalDatesTableColumns[1]+" text not null)");
        for(int i = 0;i<5;i++)
        {
            db.execSQL("insert into FestivalDates(festivalName,festivalDate) values('"+festival_Names[i]+"','"+festival_Dates[i]+"')");
        }
        db.execSQL("create table if not exists "+FestivalMessagesTable+
                "(" +
                "_id integer primary key autoincrement," +
                ""+FestivalMessagesTableColumns[0]+" integer not null," +
                ""+FestivalMessagesTableColumns[1]+" text not null," +
                "foreign key(festivalId) references FestivalDates(_id))");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(1,'劳动节快乐')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'云淡风也轻，秋叶飘满天，金秋收获季，共庆国庆节；祝您国庆佳节天天好心情，事事都如意！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'朋字双月并肩行,远隔千里两地明;祝友健康阂家乐,事业顺利展宏程;国庆佳节同喜日,捧杯聚首秋月中.')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(3,'新年新气象，百事可乐，万事七喜，心情雪碧，学习芬达，工作红牛，生活茹梦，爱情鲜橙多，天天娃哈哈，月月乐百事')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(3,'我这份祝福跨过重重高山，掠过条条小溪，跳过马路，窜出胡同，闪过卖冰糖葫芦的老太太，钻进你耳朵里－祝新年快乐！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(4,'深祝福，丝丝情谊，串串思念，化作一份礼物，留在你的心田，祝你圣诞快乐，新年幸福！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(2,'今日大寒，满天雪花飞舞，临近年关仅六天，老叟童依皆大欢；瑞雪兆丰年，龙年伊始话慨感；悦心不减当年；炮声处处映门庭；欢畅笑语迎新年！')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(1,'劳动节快乐')");
        db.execSQL("insert into "+FestivalMessagesTable+"("+FestivalMessagesTableColumns[0]+","+FestivalMessagesTableColumns[1]+") values(5,'儿童节快乐')");

        db.execSQL("create table if not exists "+MessageHistroyTable+
                "(" +
                "_id integer primary key autoincrement," +
                ""+MessageHistroyTableColumns[0]+" text not null," +
                ""+MessageHistroyTableColumns[1]+" integer not null," +
                ""+MessageHistroyTableColumns[2]+" integer not null," +
                ""+MessageHistroyTableColumns[3]+" text not null,"+
                "foreign key(festivalId) references FestivalDates(_id)," +
                "foreign key(messageId) references FestivalMessages(_id))"); //设置外键
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
