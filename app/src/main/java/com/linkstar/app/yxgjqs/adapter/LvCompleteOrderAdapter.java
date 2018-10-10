package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.activity.EvaluateActivity;
import com.linkstar.app.yxgjqs.bean.CompleteOrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/1/001.
 */

public class LvCompleteOrderAdapter extends BaseAdapter {

    private Context context;
    private List<CompleteOrderBean> data = new ArrayList<>();

    public LvCompleteOrderAdapter(Context context, List<CompleteOrderBean> data) {
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

        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_complete_order, null);
        TextView tvStatu = v.findViewById(R.id.tv_complete_order_statu);
        TextView tvTime = v.findViewById(R.id.tv_complete_order_time);
        TextView tvGetName = v.findViewById(R.id.tv_complete_order_get_name);
        TextView tvGetAdd = v.findViewById(R.id.tv_complete_order_get_add);
        TextView tvSendName = v.findViewById(R.id.tv_complete_order_send_name);
        TextView tvSendAdd = v.findViewById(R.id.tv_complete_order_send_add);
        TextView tvReson = v.findViewById(R.id.tv_complete_order_cancle_reson);
        RelativeLayout layout = v.findViewById(R.id.rLayout_complete_order_bottom);
        Button btnEva = v.findViewById(R.id.btn_to_evaluate);


        if (data.get(position).statu == 1) {
            tvStatu.setText("取件");

        } else {
            tvStatu.setText("送件");
        }

        if (data.get(position).action == 1) {
            //当前为已完成订单
            tvTime.setText("完成时间：" + data.get(position).time);
            if (data.get(position).isEvaluate == 1) {
                //未评价订单
                btnEva.setVisibility(View.VISIBLE);
            } else {
                //已评价订单
                layout.setVisibility(View.GONE);
            }
        } else {
            //当前为已取消订单
            tvTime.setText("取消时间：" + data.get(position).time);
            tvReson.setVisibility(View.VISIBLE);
            tvReson.setText("取消原因：" + data.get(position).reson);
        }
        tvGetName.setText(data.get(position).name_from);
        tvGetAdd.setText(data.get(position).add_from);
        tvSendName.setText(data.get(position).name_to);
        tvSendAdd.setText(data.get(position).add_to);

        btnEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EvaluateActivity.class);
                context.startActivity(intent);
            }
        });
        return v;
    }
}
