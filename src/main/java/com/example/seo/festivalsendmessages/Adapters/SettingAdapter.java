package com.example.seo.festivalsendmessages.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.seo.festivalsendmessages.Activitys.AddMessageModel;
import com.example.seo.festivalsendmessages.Activitys.AddRemindActivity;
import com.example.seo.festivalsendmessages.Activitys.MainActivity;
import com.example.seo.festivalsendmessages.Beans.FestivalDateBean;
import com.example.seo.festivalsendmessages.Beans.SettingItemBean;
import com.example.seo.festivalsendmessages.R;
import com.example.seo.festivalsendmessages.utils.CommonAdapter;
import com.example.seo.festivalsendmessages.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/6/20.
 */
public class SettingAdapter extends CommonAdapter<SettingItemBean> implements OnClickListener{

    private SQLiteDatabase database;
    private int[] dialogViews = {R.layout.addremindate,R.layout.addmessagemodel};
    private List<FestivalDateBean> beans = new ArrayList<>();
    private Context context;
    private AlertDialog mDialog;
    private String spinner_FestivalId;

    public SettingAdapter(Context mContext, List<SettingItemBean> dataList, int layoutID) {
        super(mContext, dataList, layoutID);
        context = mContext;
        database = MainActivity.database;
    }

    @Override
    public void setConvertData(ViewHolder holder, SettingItemBean settingItemBean) {
        LinearLayout layout = holder.getView(R.id.setting_item_layout);
        layout.setOnClickListener(this);
        ImageView imageView = holder.getView(R.id.setting_item_icon);
        imageView.setImageResource(settingItemBean.getItemIcon());
        TextView textView = holder.getView(R.id.setting_item_text);
        textView.setText(settingItemBean.getItemText());
    }

    @Override
    public void onClick(final View v) {
        Intent intent = null;
        TextView textView = (TextView) v.findViewById(R.id.setting_item_text);
        if(textView != null) {
            switch (textView.getText().toString().trim()) {
                case "添加提醒日期":
                    //setRemindDialog(mDialog);
                    intent = new Intent(mContext, AddRemindActivity.class);
                    mContext.startActivity(intent);
                    break;
                case "添加短信模板":
                    intent = new Intent(mContext, AddMessageModel.class);
                    mContext.startActivity(intent);
                    break;
            }
        }
        /*switch (v.getId())
        {
            case R.id.select_date:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int monthOfYear = calendar.get(Calendar.MONTH);
                int dayOfMaonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(mDialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ((Button)v).setText((monthOfYear+1) + "-" + dayOfMonth +"");
                        v.setTag(true);
                        v.setFocusable(true);
                        v.setFocusableInTouchMode(true);
                        v.requestFocus();
                    }
                },year,monthOfYear,dayOfMaonth);
                dateDialog.show();
                break;
            case R.id.add_Remind:
                Window window = mDialog.getWindow();
                Button itemButton = (Button) window.findViewById(R.id.select_date);
                EditText itemEdit = (EditText) window.findViewById(R.id.edit_date);
                if(((boolean)itemButton.getTag()) && itemEdit.getText().toString().trim() != null)
                {
                    ContentValues value = new ContentValues();
                    value.put(DbOpenHelper.FestivalDatesTableColumns[0],itemEdit.getText().toString().trim());
                    value.put(DbOpenHelper.FestivalDatesTableColumns[1],itemButton.getText().toString().trim());
                    database.insert(DbOpenHelper.FestivalDatesTable,null,value);
                    ((AlertDialog)v.getTag()).cancel();
                }
                else {
                    if(itemEdit.getText().toString().trim() == null)
                    {
                        Toast.makeText(context, "请输入提示名称", Toast.LENGTH_SHORT).show();
                    }
                    if(((boolean)itemButton.getTag()) != true)
                    {
                        Toast.makeText(context, "请选择日期", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.add_MessageModel:
                String message = ((TextView)mDialog.getWindow().findViewById(R.id.edit_message_model)).getText().toString().trim();
                Cursor cursor = database.query(DbOpenHelper.FestivalMessagesTable,null,DbOpenHelper.FestivalDatesTableColumns[1] + "=?" , new String[]{message},null,null,null);
                if(cursor.getCount() >= 1)
                {
                    Toast.makeText(context, "请不要输入重复的信息内容", Toast.LENGTH_SHORT).show();
                    cursor.close();
                }
                else {
                    ContentValues value = new ContentValues();
                    value.put(DbOpenHelper.FestivalMessagesTableColumns[0],spinner_FestivalId);
                    value.put(DbOpenHelper.FestivalMessagesTableColumns[1],message);
                    database.insert(DbOpenHelper.FestivalMessagesTable,null,value);
                    ((AlertDialog)v.getTag()).cancel();
                }
                break;
        }*/
    }

/*    private void setRemindDialog(AlertDialog dialog)
    {
        Window window = dialog.getWindow();
        window.setContentView(dialogViews[0]);
        Button select = (Button) window.findViewById(R.id.select_date);
        select.setOnClickListener(this);
        Button addRemind = (Button) window.findViewById(R.id.add_Remind);
        addRemind.setTag(dialogViews[0]);
        addRemind.setOnClickListener(this);
    }*/

    /*private void setMessageDialog(AlertDialog dialog)
    {
        Cursor cursor = database.query(DbOpenHelper.FestivalDatesTable,null,null,null,null,null,"_id");
        if(cursor.moveToFirst())
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
        Window window = dialog.getWindow();
        window.setContentView(R.layout.addmessagemodel);
        Spinner festivalSpinner = (Spinner) window.findViewById(R.id.spinner);
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context,beans,R.layout.spinner_item);
        festivalSpinner.setOnItemSelectedListener(this);
        festivalSpinner.setAdapter(spinnerAdapter);
        Button button = (Button) window.findViewById(R.id.add_MessageModel);
        button.setOnClickListener(this);
    }*/
}
