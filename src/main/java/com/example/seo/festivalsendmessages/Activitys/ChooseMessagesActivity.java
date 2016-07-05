package com.example.seo.festivalsendmessages.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.example.seo.festivalsendmessages.Adapters.ChooseMessageBaseAdapter;
import com.example.seo.festivalsendmessages.Beans.FestivalMessageBean;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.fragments.FestivalSeleterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/3/9.
 */
public class ChooseMessagesActivity extends Activity implements OnClickListener{

    private ListView listView;
    private ChooseMessageBaseAdapter adapter;
    private int festivalId;
    private FloatingActionButton m_Fab_ToSend;
    public static List<FestivalMessageBean> festivalMessageBeans = new ArrayList<FestivalMessageBean>();
    public static final String KEY_NOMODELMESSAGE = "noModelMessage"; //不使用模板KEY
    public static final String FESTIVAL_ID = "FESTIVAL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosemessage_activity);
        festivalId = getIntent().getIntExtra(FestivalSeleterFragment.ID_FESTIVAL,-1);
        initDatas();
        initViews();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        festivalMessageBeans.clear();
    }

    public void initViews()
    {
        m_Fab_ToSend = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        m_Fab_ToSend.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.id_listview);
        listView.setAdapter(adapter);
    }

    public void initDatas()
    {
        Cursor cursor = MainActivity.database.rawQuery("select * from "+ DbOpenHelper.FestivalMessagesTable+" where "+DbOpenHelper.FestivalMessagesTableColumns[0]+"= "+festivalId+" order by _id",null);
        if(cursor != null)
        {
            while (cursor.moveToNext())
            {
                int beanMessageId = cursor.getInt(cursor.getColumnIndex("_id"));
                int beanFestivalId = cursor.getInt(cursor.getColumnIndex(DbOpenHelper.FestivalMessagesTableColumns[0]));
                String beanMessage = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalMessagesTableColumns[1]));
                FestivalMessageBean bean = new FestivalMessageBean(beanFestivalId,beanMessageId,beanMessage);
                festivalMessageBeans.add(bean);
            }
        }
        adapter = new ChooseMessageBaseAdapter(ChooseMessagesActivity.this,festivalMessageBeans,R.layout.list_item);
        cursor.close();
    }

    public void initEvents()
    {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SendMessagesActivity.class);
        intent.putExtra(KEY_NOMODELMESSAGE,true);
        intent.putExtra(FESTIVAL_ID,festivalId);
        startActivity(intent);
    }
}
