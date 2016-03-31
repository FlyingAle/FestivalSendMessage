package com.example.seo.festivalsendmessages.Adapters;

import android.content.Context;

import com.example.seo.festivalsendmessages.Beans.MessageHistoryBean;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.utils.CommonAdapter;
import com.example.seo.festivalsendmessages.utils.ViewHolder;

import java.util.List;

/**
 * Created by Seo on 2016/3/24.
 */
public class MessageHistoryBaseAdapter extends CommonAdapter<MessageHistoryBean> {

    public MessageHistoryBaseAdapter(Context mContext, List<MessageHistoryBean> dataList, int layoutID) {
        super(mContext, dataList, layoutID);
    }

    @Override
    public void setConvertData(ViewHolder holder, MessageHistoryBean messageHistoryBean) {
        holder.setTextView_Text(R.id.history_Tv_ContactName,messageHistoryBean.getContactName()).setTextView_Text(R.id.hisory_Tv_PhoneNumber,messageHistoryBean.getAcceptanceNumber()).setTextView_Text(R.id.history_Tv_Message,messageHistoryBean.getMessage());
    }
}
