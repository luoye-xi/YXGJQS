package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.linkstar.app.yxgjqs.R;

/**
 * Created by hx
 * Time 2018/8/1/001.
 */

public class LvReceiptAdapter extends BaseAdapter{
    private Context context;
    private MyClickListener mlistener;

    public LvReceiptAdapter(Context context,MyClickListener listener) {
        this.context = context;
        this.mlistener = listener;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_wait_receipt,null);
        Button btnRefuse = v.findViewById(R.id.btn_refuse_order);
        ImageView imgPickPhone = v.findViewById(R.id.img_re_order_pick_phone);
        ImageView imgPickAdd = v.findViewById(R.id.img_re_order_pick_address);
        ImageView imgSenfPhone = v.findViewById(R.id.img_re_order_send_phone);
        ImageView imgSendAdd = v.findViewById(R.id.img_re_order_send_address);
        imgPickPhone.setOnClickListener(mlistener);
        imgPickAdd.setOnClickListener(mlistener);
        imgSenfPhone.setOnClickListener(mlistener);
        imgSendAdd.setOnClickListener(mlistener);
        btnRefuse.setOnClickListener(mlistener);
        imgPickPhone.setTag(position);
        imgPickAdd.setTag(position);
        imgSenfPhone.setTag(position);
        imgSendAdd.setTag(position);
        btnRefuse.setTag(position);
        return v;
    }

    /**
     * 用于回调的抽象类
     * @author Ivan Xu
     * 2014-11-26
     */
    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }
        public abstract void myOnClick(int position, View v);
    }
}
