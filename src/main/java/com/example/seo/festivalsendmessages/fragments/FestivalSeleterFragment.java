package com.example.seo.festivalsendmessages.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.seo.festivalsendmessages.Activitys.MainActivity;
import com.example.seo.festivalsendmessages.Adapters.FestivalListAdapter;
import com.example.seo.festivalsendmessages.Beans.FestivalDateBean;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/3/7.
 */
public class FestivalSeleterFragment extends android.support.v4.app.Fragment{

    public static final String ID_FESTIVAL = "ID_FESTIVAL";
//    private FlowLayout flowLayout;
    private View view;
    private static SQLiteDatabase database;
    public  List<FestivalDateBean> festivalDateBeans ;
    private ListView mFestivalListView;
    private  FestivalListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.festival_select,container,false);
        database = MainActivity.database;
        festivalDateBeans = new ArrayList<FestivalDateBean>();
        initDatas();
        initViews();
        return view;
    }

    public void initViews()
    {
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        mFestivalListView = (ListView) view.findViewById(R.id.FestivalList);
        mFestivalListView.setAdapter(adapter);
/*        flowLayout = (FlowLayout) view.findViewById(R.id.festival_flowLayout);
        for (int i=0;i<festivalDateBeans.size();i++)
        {
            FestivalDateBean bean = festivalDateBeans.get(i);
            Button button = (Button) inflater.inflate(R.layout.button,flowLayout,false);
            button.setText(bean.getFestivalName());
            button.setTag(bean.getFestivalId());
            button.setOnClickListener(this);
            flowLayout.addView(button);
        }*/
    }

    public  void initDatas()
    {
        String festivalName;
        int festivalId;
        String festivalDate;
        Cursor cursor = database.query(DbOpenHelper.FestivalDatesTable,null,null,null,null,null,"_id");
        if(cursor!=null) {
            while(cursor.moveToNext())
            {
                festivalId = cursor.getInt(cursor.getColumnIndex("_id"));
                festivalName = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalDatesTableColumns[0]));
                festivalDate = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalDatesTableColumns[1]));
                FestivalDateBean bean = new FestivalDateBean(festivalId,festivalName,festivalDate);
                festivalDateBeans.add(bean);
            }
        }
        adapter = new FestivalListAdapter(getContext(),festivalDateBeans,R.layout.festival_item);
    }
}
