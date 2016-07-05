package com.example.seo.festivalsendmessages.Activitys;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.example.seo.festivalsendmessages.Adapters.MyFragmentPagerAdapter;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.Services.TimerServices;
import com.example.seo.festivalsendmessages.fragments.FestivalSeleterFragment;
import com.example.seo.festivalsendmessages.fragments.MessagesHistoryFragment;
import com.example.seo.festivalsendmessages.fragments.SettingFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener,OnPageChangeListener{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MyFragmentPagerAdapter adapter;
    private List<Fragment> fragmentsList;
    private Fragment[] fragments = {new FestivalSeleterFragment(),new MessagesHistoryFragment(),new SettingFragment()};
    private int[] tab_Default_Icon = {R.drawable.timer,R.drawable.send,R.drawable.setting};
    private int[] tab_Click_Icon = {R.drawable.timer_doclick,R.drawable.send_doclick,R.drawable.setting_doclick};
    public DbOpenHelper helper = new DbOpenHelper(this,"FestivalDataBase",null,1);
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(database!=null && database.isOpen())
        {
            database.close();
        }
        if(!serviceIsWorking("com.example.seo.festivalsendmessage.Services.TimerServices"))
        {
            Intent intent = new Intent(this, TimerServices.class);
            startService(intent);
        }
        initDatas();
        initViews();
        initEvents();
    }

    public void setImmersiveBar()
    {
        Window window = getWindow();
        Class clazz = window.getClass(); try {
        int tranceFlag = 0;
        int darkModeFlag = 0;
        Class layoutParams = null;
            layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);
            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (NoSuchMethodException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    }
        //只需要状态栏透明 extraFlagField.invoke(window, tranceFlag, tranceFlag); 或
        // 状态栏透明且黑色字体 extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);
        // 清除黑色字体 extraFlagField.invoke(window, 0, darkModeFlag); } catch (NoSuchMethodException e) { e.printStackTrace(); } catch (ClassNotFoundException e) { e.printStackTrace(); } catch (NoSuchFieldException e) { e.printStackTrace(); } catch (IllegalAccessException e) { e.printStackTrace(); } catch (IllegalArgumentException e) { e.printStackTrace(); } catch (InvocationTargetException e) { e.printStackTrace(); }
    }

    public  boolean serviceIsWorking(String className)
    {
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> infos = (ArrayList<ActivityManager.RunningServiceInfo>) manager.getRunningServices(100);
        for(int i=0;i<infos.size();i++)
        {
            if(infos.get(i).service.getClassName().toString().equals(className))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!database.isOpen())
        {
            database = helper.getWritableDatabase();
        }
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
        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
        for(int i=0;i<mTabLayout.getTabCount();i++)
        {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if(tab != null)
            {
                tab.setCustomView(adapter.getTabView(i));
                if(tab.getCustomView() != null)
                {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                    tabView.setOnClickListener(this);
                }
            }
        }
    }

    public void initEvents()
    {

    }

    public void initDatas()
    {
        database = helper.getWritableDatabase();
        fragmentsList = new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            Fragment fragment = fragments[i];
            fragmentsList.add(fragment);
        }
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentsList,MainActivity.this);
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

    @Override
    public void onClick(View v) {
        setTab_Default_Icon();
        int pos = (int) v.getTag();
        TabLayout.Tab tab = mTabLayout.getTabAt(pos);
        View tabView = (View) tab.getCustomView().getParent();
        ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_Icon);
        imageView.setImageResource(tab_Click_Icon[pos]);
        mViewPager.setCurrentItem(pos);
    }

    public void setTab_Default_Icon()
    {
        for(int i=0;i<mTabLayout.getTabCount();i++)
        {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View tabView = (View) tab.getCustomView().getParent();
            ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_Icon);
            imageView.setImageResource(tab_Default_Icon[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTab_Default_Icon();
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        View tabView = (View) tab.getCustomView().getParent();
        ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_Icon);
        imageView.setImageResource(tab_Click_Icon[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
