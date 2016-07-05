package com.example.seo.festivalsendmessages.Adapters;

import android.content.Context;
import android.widget.TextView;

import com.example.seo.festivalsendmessages.Beans.FestivalDateBean;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.utils.CommonAdapter;
import com.example.seo.festivalsendmessages.utils.ViewHolder;

import java.util.List;

/**
 * Created by Seo on 2016/6/20.
 */
public class SpinnerAdapter extends CommonAdapter<FestivalDateBean> {

    private List<FestivalDateBean> beans;

    public SpinnerAdapter(Context mContext, List<FestivalDateBean> dataList, int layoutID) {
        super(mContext, dataList, layoutID);
        beans = dataList;
    }

    @Override
    public void setConvertData(ViewHolder holder, FestivalDateBean festivalDateBean) {
        TextView festivalId = holder.getView(R.id.spinner_Festival_Id);
        TextView festivalDate = holder.getView(R.id.spinner_Festival_Date);
        TextView festivalName = holder.getView(R.id.spinner_Festival_Name);
        festivalDate.setText(festivalDateBean.getFestivalDate());
        festivalName.setText(festivalDateBean.getFestivalName());
        festivalId.setText(String.valueOf(festivalDateBean.getFestivalId()));
    }
    public int getBeansId(int position)
    {
        int id = beans.get(position).getFestivalId();
        return id;
    }
}
