package com.example.seo.festivalsendmessages.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.seo.festivalsendmessages.Activitys.ChooseMessagesActivity;
import com.example.seo.festivalsendmessages.Beans.FestivalDateBean;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.utils.CommonAdapter;
import com.example.seo.festivalsendmessages.utils.ViewHolder;

import java.util.List;

/**
 * Created by Seo on 2016/6/4.
 */
public class FestivalListAdapter extends CommonAdapter<FestivalDateBean> implements OnClickListener{

    Context mContext;
    public static final String ID_FESTIVAL = "ID_FESTIVAL";

    public FestivalListAdapter(Context mContext, List<FestivalDateBean> dataList, int layoutID) {
        super(mContext, dataList, layoutID);
        this.mContext = mContext;
    }

    @Override
    public void setConvertData(ViewHolder holder, FestivalDateBean festivalDateBean) {
        Button button = holder.getView(R.id.festival_name);
        button.setText(festivalDateBean.getFestivalName());
        button.setTag(festivalDateBean.getFestivalId());
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext,ChooseMessagesActivity.class);
        intent.putExtra(ID_FESTIVAL,(int)v.getTag());
        mContext.startActivity(intent);
    }
}
