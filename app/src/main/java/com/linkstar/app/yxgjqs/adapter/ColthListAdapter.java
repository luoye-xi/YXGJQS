package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.bean.ClothBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/1/001.
 */

public class ColthListAdapter extends BaseAdapter {
    private Context context;
    private List<ClothBean> data = new ArrayList<>();

    public ColthListAdapter(Context context, List<ClothBean> data) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_goods_list,null);
        ImageView img = v.findViewById(R.id.img_cloth_type);
        TextView tvPrice = v.findViewById(R.id.tv_cloth_type_price);

        img.setImageResource(data.get(position).img);
        tvPrice.setText(data.get(position).price);
        return v;
    }
}
