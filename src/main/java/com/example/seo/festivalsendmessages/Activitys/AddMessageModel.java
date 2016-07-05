package com.example.seo.festivalsendmessages.Activitys;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seo.festivalsendmessages.Adapters.SpinnerAdapter;
import com.example.seo.festivalsendmessages.Beans.FestivalDateBean;
import com.example.seo.festivalsendmessages.DbHelpers.DbOpenHelper;
import com.example.seo.festivalsendmessages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/6/26.
 */
public class AddMessageModel extends Activity implements OnClickListener,OnItemSelectedListener{

    private Spinner mFestivalSpinner;
    private EditText mMessageEditText;
    private Button mAddMessageModel;
    private SpinnerAdapter adapter;
    private SQLiteDatabase database = MainActivity.database;
    private List<FestivalDateBean> beans = new ArrayList<>();
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmessagemodel);
        initDatas();
        initViews();
    }

    private void initDatas() {
        Cursor cursor = database.query(DbOpenHelper.FestivalDatesTable,null,null,null,null,null,"_id");
        if(cursor != null)
        {
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String festivalName = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalDatesTableColumns[0]));
                String festivalDate = cursor.getString(cursor.getColumnIndex(DbOpenHelper.FestivalDatesTableColumns[1]));
                FestivalDateBean bean = new FestivalDateBean(id,festivalName,festivalDate);
                beans.add(bean);
            }
        }
        cursor.close();
        adapter = new SpinnerAdapter(this,beans,R.layout.spinner_item);
    }

    private void initViews() {
        mFestivalSpinner = (Spinner) findViewById(R.id.festival_spinner);
        mMessageEditText = (EditText) findViewById(R.id.message_edittext);
        mAddMessageModel = (Button) findViewById(R.id.add_messageModel);
        mFestivalSpinner.setAdapter(adapter);
        mFestivalSpinner.setOnItemSelectedListener(this);
        mAddMessageModel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String message = mMessageEditText.getText().toString().trim();
                if(message != "") {
                    Cursor cursor = database.query(DbOpenHelper.FestivalMessagesTable, null, DbOpenHelper.FestivalMessagesTableColumns[1] + "=?", new String[]{message}, null, null, null);
                    if (cursor.getCount() == 0) {
                        ContentValues value = new ContentValues();
                        value.put(DbOpenHelper.FestivalMessagesTableColumns[0],String.valueOf(adapter.getBeansId(itemPosition)));
                        value.put(DbOpenHelper.FestivalMessagesTableColumns[1],message);
                        database.insert(DbOpenHelper.FestivalMessagesTable,null,value);
                    }
                    else
                    {
                        Toast.makeText(AddMessageModel.this, "请不要输入重复的短信模版", Toast.LENGTH_SHORT).show();
                        mMessageEditText.setText("");
                    }
                    cursor.close();
                }
        else {
                    Toast.makeText(AddMessageModel.this, "请先输入短信模版后再添加", Toast.LENGTH_SHORT).show();
                }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        itemPosition = position;
        mMessageEditText.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
