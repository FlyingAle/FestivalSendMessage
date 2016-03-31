package com.example.seo.festivalsendmessages.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Seo on 2016/2/17.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{

    protected Context mContext;
    protected LayoutInflater inflater;
    protected List<T> dataList;
    protected ViewHolder viewHolder;
    private int layoutID;

    public CommonAdapter(Context mContext, List<T> dataList,int layoutID) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.dataList = dataList;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder = ViewHolder.get(mContext,position,convertView,layoutID,parent);
        setConvertData(viewHolder,getItem(position));
        return viewHolder.getItem_convertViews();
    }

    public abstract void setConvertData(ViewHolder holder,T t);

}
