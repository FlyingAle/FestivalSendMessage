package com.example.seo.festivalsendmessages.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.seo.festivalsendmessages.Adapters.SettingAdapter;
import com.example.seo.festivalsendmessages.Beans.SettingItemBean;
import com.example.seo.festivalsendmessages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/6/15.
 */
public class SettingFragment extends Fragment{

    private String[] itemText = {"添加提醒日期","添加短信模板"};
    private int[] itemIcon = {R.drawable.timer,R.drawable.send};
    private ListView listView;
    private List<SettingItemBean> beanList = new ArrayList<>();
    private View view;
    private SettingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_layout,container,false);
        initViews();
        return view;
    }

    private void initViews() {
        listView = (ListView) view.findViewById(R.id.setting_list);
        adapter = new SettingAdapter(getContext(),beanList,R.layout.setting_list_item);
        listView.setAdapter(adapter);
    }

    private void initDatas() {
        for(int i=0;i<itemText.length;i++)
        {
            SettingItemBean bean = new SettingItemBean(itemText[i],itemIcon[i]);
            beanList.add(bean);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

