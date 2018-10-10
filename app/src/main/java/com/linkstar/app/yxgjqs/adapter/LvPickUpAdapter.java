package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.activity.PhotoEvidenceActivity;
import com.linkstar.app.yxgjqs.activity.RequestGetActivity;
import com.linkstar.app.yxgjqs.activity.ValuationActivity;
import com.linkstar.app.yxgjqs.bean.PickUpBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/7/31/031.
 */

public class LvPickUpAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<PickUpBean> data = new ArrayList<>();
    private Callback mCallback;

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     *
     * @author Ivan Xu
     *         2014-11-26
     */
    public interface Callback {
        public void click(View v);
    }

    public LvPickUpAdapter(Context context, List<PickUpBean> data, Callback callback) {
        this.context = context;
        this.data = data;
        this.mCallback = callback;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_pick_up, null);
        TextView tvStatu = v.findViewById(R.id.tv_pick_statu);
        TextView tvTime = v.findViewById(R.id.tv_pick_item_time);
        TextView tvPS = v.findViewById(R.id.btn_to_way);
        TextView tvTH = v.findViewById(R.id.btn_to_home);
        ImageView imgPickPhone = v.findViewById(R.id.img_wait_pick_phone);
        ImageView imgPickAdd = v.findViewById(R.id.img_wait_pick_address);
        ImageView imgSendPhone = v.findViewById(R.id.img_wait_send_phone);
        ImageView imgSendAdd = v.findViewById(R.id.img_wait_send_address);
        tvPS.setTag(position);
        imgPickPhone.setTag(position);
        imgPickAdd.setTag(position);
        imgSendPhone.setTag(position);
        imgSendAdd.setTag(position);

        if (data.get(position).statu == 1) {
            tvStatu.setText("取件");
        } else {
            tvStatu.setText("送件");
        }

        if (data.get(position).type == 1) {
            tvTime.setText("预约取：今天" + data.get(position).time);
            tvTH.setText("确定上门");
        } else if (data.get(position).type == 2){
            tvTime.setText("剩余时间：" + data.get(position).time + "分钟");
            tvTH.setText("请求签收");
        }else if (data.get(position).type == 3){
            tvTime.setText("预约取：今天" + data.get(position).time);
            tvTH.setText("等待付款");
            tvPS.setVisibility(View.GONE);
        }

        tvTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (data.get(position).type == 1) {
                    intent = new Intent(context, PhotoEvidenceActivity.class);
                    intent.putExtra("action_statu", data.get(position).statu);
                    context.startActivity(intent);
                } else  if (data.get(position).type == 2) {
                    intent = new Intent(context, RequestGetActivity.class);
                    intent.putExtra("action_statu", data.get(position).statu);
                    context.startActivity(intent);
                } else  if (data.get(position).type == 3) {
                    intent = new Intent(context, ValuationActivity.class);
                    intent.putExtra("action_statu", data.get(position).statu);
                    context.startActivity(intent);
                }
            }
        });
        tvPS.setOnClickListener(this);
        imgPickPhone.setOnClickListener(this);
        imgPickAdd.setOnClickListener(this);
        imgSendPhone.setOnClickListener(this);
        imgSendAdd.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}
