package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.bean.HomeMenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/7/31/031.
 */

public class GvHomeMenuAdapter extends BaseAdapter {
    private Context context;
    private List<HomeMenuBean> data = new ArrayList<>();

    public GvHomeMenuAdapter(Context context, List<HomeMenuBean> data) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.item_gv_home_menu,null);
        ImageView  img = v.findViewById(R.id.img_item_home_menu);
        TextView tv = v.findViewById(R.id.tv_item_home_menu);
        TextView tvPoint = v.findViewById(R.id.tv_item_home_num_point);

        img.setImageResource(data.get(position).img);
        tv.setText(data.get(position).name);

        for (int i=0;i<data.size();i++){
            if (position<2){
                tvPoint.setText(3*(position+1)+"");
                tvPoint.setVisibility(View.VISIBLE);
            }
        }

        return v;
    }
}
