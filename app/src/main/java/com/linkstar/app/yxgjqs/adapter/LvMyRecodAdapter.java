package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.bean.MyRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/7/31/031.
 */

public class LvMyRecodAdapter extends BaseAdapter {
    private Context context;
    private List<MyRecordBean> data = new ArrayList<>();

    public LvMyRecodAdapter(Context context, List<MyRecordBean> data) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_my_recod,null);
        TextView tvNum = v.findViewById(R.id.tv_item_recod_num);
        TextView tvName =  v.findViewById(R.id.tv_item_recod_name);

        tvNum.setText(data.get(position).num);
        tvName.setText(data.get(position).name);
        return v;
    }
}
