package com.example.seo.festivalsendmessages.Activitys;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.seo.festivalsendmessages.Adapters.MyFragmentPagerAdapter;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.fragments.FestivalSeleterFragment;
import com.example.seo.festivalsendmessages.fragments.MessagesHistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MyFragmentPagerAdapter adapter;
    private List<Fragment> fragmentsList;
    private Fragment[] fragments = {new FestivalSeleterFragment(),new MessagesHistoryFragment()};
    private String[] viewPager_titles = {"节日短信","发送记录"};
    private DbOpenHelper helper = new DbOpenHelper(this,"FestivalDataBase",null,1);
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initViews();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    public void initViews()
    {
        mViewPager = (ViewPager) findViewById(R.id.viewPager_main);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout_Main);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void initEvents()
    {

    }

    public void initDatas()
    {
        database = helper.getWritableDatabase();
        fragmentsList = new ArrayList<Fragment>();
        for(int i=0;i<2;i++)
        {
            Fragment fragment = fragments[i];
            fragmentsList.add(fragment);
        }
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentsList,viewPager_titles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
