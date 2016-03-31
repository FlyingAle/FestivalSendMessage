package com.example.seo.festivalsendmessages.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.seo.festivalsendmessages.Beans.FestivalMessageBean;
import com.example.seo.festivalsendmessages.R;

import java.util.List;

/**
 * Created by Seo on 2016/3/9.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<FestivalMessageBean> beans ;
/*    private int festivalId;

    public MyRecyclerViewAdapter(int festivalId)
    {
        this.festivalId = festivalId;
        beans = ChooseMessagesActivity.festivalMessageBeans;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.cardview,parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FestivalMessageBean bean = beans.get(position);
            holder.setMessage_TextView(bean.getMessage());
    }

    @Override
    public int getItemCount() {
/*        int count = 0;
        for (int i =0 ;i<beans.size();i++)
        {
            FestivalMessageBean bean = beans.get(i);
            if(bean.getFestivalId() == festivalId)
            {
                count++;
            }
        }*/
        return beans.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView message_TextView;
        private Button send_Button;

        public ViewHolder(View itemView) {
            super(itemView);
            message_TextView = (TextView) itemView.findViewById(R.id.msg_TextView);
            send_Button = (Button) itemView.findViewById(R.id.msg_Button_ToSend);
        }

        public void setMessage_TextView(String message)
        {
            message_TextView.setText(message);
        }
    }
}
