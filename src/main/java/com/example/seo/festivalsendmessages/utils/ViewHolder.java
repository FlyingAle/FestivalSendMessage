package com.example.seo.festivalsendmessages.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Seo on 2016/2/15.
 */
public class ViewHolder {

    private SparseArray<View> item_Views;
    private int item_positions;

    public View getItem_convertViews() {
        return item_convertViews;
    }

    private View item_convertViews;

    public ViewHolder(Context context,int position, View convertView, ViewGroup parent,int layoutId)
    {
        item_convertViews = LayoutInflater.from(context).inflate(layoutId,parent,false);
        item_positions = position;
        item_convertViews.setTag(this);
        item_Views = new SparseArray<View>();
    }
    public static ViewHolder get(Context context,int position,View convertView,int layoutId, ViewGroup parent)
    {
        if(convertView == null)
        {
            return new ViewHolder(context,position,convertView,parent,layoutId);
        }
        else
        {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.item_positions = position;
            return holder;
        }
    }

    public ViewHolder setTextView_Text(int viewId,String text)
    {
        ((TextView)getView(viewId)).setText(text);
        return this;
    }

    public <T extends View> T getView(int viewId)
    {
        View view = item_Views.get(viewId);
        if(view == null)
        {
            view = item_convertViews.findViewById(viewId);
            item_Views.put(viewId,view);
        }
        return (T) view;
    }
}
