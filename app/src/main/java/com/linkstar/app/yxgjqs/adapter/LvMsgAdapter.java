package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/7/31/031.
 */

public class LvMsgAdapter extends BaseAdapter {

    private Context context;
    private List<MessageBean> data = new ArrayList<>();

    public LvMsgAdapter(Context context, List<MessageBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_msg_list,null);
        TextView tvTime = v.findViewById(R.id.tv_msg_receive_time);
        TextView tvTitle = v.findViewById(R.id.tv_msg_title);
        TextView tvContent = v.findViewById(R.id.tv_msg_content);

        tvTime.setText(data.get(position).time);
        tvTitle.setText(data.get(position).title);
        tvContent.setText(data.get(position).msg);

        return v;
    }
}
