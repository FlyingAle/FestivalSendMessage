package com.example.seo.festivalsendmessages.Activitys;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;

/**
 * Created by Seo on 2016/6/24.
 */
public class AddRemindActivity extends Activity implements OnClickListener,OnDateChangeListener{

    private CalendarView mCalenderView;
    private int calenderViewHeight;
    private EditText mEditText;
    private Button mButton;
    private String remindDate;
    private SQLiteDatabase database = MainActivity.database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addremindate);
        initDatas();
        initViews();
    }

    private void initViews() {
        mCalenderView = (CalendarView) findViewById(R.id.calendarView);
        mCalenderView.setOnDateChangeListener(this);
        calenderViewHeight = mCalenderView.getMeasuredHeight();
        mEditText = (EditText) findViewById(R.id.remind_name);
        mEditText.setOnClickListener(this);
        mButton = (Button) findViewById(R.id.add_remind);
        mButton.setOnClickListener(this);
    }

    private void initDatas() {

    }

    @Override
    public void onClick(View v) {
        String remindName = mEditText.getText().toString().trim();
        switch (v.getId()) {
            case R.id.add_remind:
            if (remindName != "") {
                if (remindDate != null) {
                    Cursor cursor = database.query(DbOpenHelper.FestivalDatesTable, null, DbOpenHelper.FestivalDatesTableColumns[0] + "=?", new String[]{remindName}, null, null, null);
                    if (cursor.getCount() == 0) {
                        ContentValues values = new ContentValues();
                        values.put(DbOpenHelper.FestivalDatesTableColumns[0], remindName);
                        values.put(DbOpenHelper.FestivalDatesTableColumns[1], remindDate);
                        database.insert(DbOpenHelper.FestivalDatesTable, null, values);
                        Toast.makeText(AddRemindActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        mEditText.setText("");
                    }
                    cursor.close();
                } else {
                    Toast.makeText(AddRemindActivity.this, "请选择日期", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddRemindActivity.this, "请输入提醒名称", Toast.LENGTH_SHORT).show();
            }
                break;
        }
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        remindDate = (month+1) + "-" + dayOfMonth;
    }
}
