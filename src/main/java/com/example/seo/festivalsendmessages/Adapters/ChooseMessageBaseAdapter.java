package com.example.seo.festivalsendmessages.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.seo.festivalsendmessages.Activitys.SendMessagesActivity;
import com.example.seo.festivalsendmessages.Beans.FestivalMessageBean;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.utils.CommonAdapter;
import com.example.seo.festivalsendmessages.utils.ViewHolder;

import java.util.List;

/**
 * Created by Seo on 2016/3/22.
 */
public class ChooseMessageBaseAdapter extends CommonAdapter<FestivalMessageBean> implements View.OnClickListener{

    private Context mContext;
    private FestivalMessageBean bean;

    public static final String BUNDLEKEY = "INTENTKEY";
    public static final String INTENTKEY = "BUNDLEKEY";//使用模板KEY

    public ChooseMessageBaseAdapter(Context mContext, List<FestivalMessageBean> dataList, int layoutID) {
        super(mContext, dataList, layoutID);
        this.mContext = mContext;
    }

    @Override
    public void setConvertData(ViewHolder holder, FestivalMessageBean festivalMessageBean) {
        bean = festivalMessageBean;
        TextView textView = holder.getView(R.id.item_Tv_message);
        textView.setText(festivalMessageBean.getMessage());
        Button button = holder.getView(R.id.item_Btn_Tosend);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, SendMessagesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLEKEY,bean);
        intent.putExtra(INTENTKEY,bundle);
        mContext.startActivity(intent);
    }
}
