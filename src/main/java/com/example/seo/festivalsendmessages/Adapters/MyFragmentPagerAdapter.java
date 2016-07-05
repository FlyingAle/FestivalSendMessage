package com.example.seo.festivalsendmessages.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.seo.festivalsendmessages.R;

import java.util.List;

/**
 * Created by Seo on 2016/2/25.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;
    private String[] viewPager_titles = {"提醒列表","短信发送历史","设置"};
    private int[] tab_Icon = {R.drawable.timer_doclick,R.drawable.send,R.drawable.setting};
    private Context mContext;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,Context context) {
        super(fm);
        this.fragments = fragmentList;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public View getTabView(int position)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_Icon);
        imageView.setImageResource(tab_Icon[position]);
        return view;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
