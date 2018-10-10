package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.bean.TransportBean;
import com.linkstar.app.yxgjqs.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/3/003.
 */

public class TransportInfoAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<TransportBean> data = new ArrayList();
    private Callback mCallback;

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     *
     * @author hx
     */
    public interface Callback {
        public void click(View v);
    }

    public TransportInfoAdapter(Context context, List<TransportBean> data, Callback callback) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_transport_info, null);
        TextView tvStatu = v.findViewById(R.id.tv_item_trans_statu);
        CircleImageView cimg = v.findViewById(R.id.cimg_transport_personal);
        TextView tvName = v.findViewById(R.id.tv_item_trans_name);
        TextView tvQS = v.findViewById(R.id.tv_item_trans_qs);
        TextView tvDesc = v.findViewById(R.id.tv_item_trans_desc);
        TextView tvBZ = v.findViewById(R.id.tv_item_trans_beizhu);
        ImageView img = v.findViewById(R.id.img_link_this);

        cimg.setImageResource(data.get(position).img);
        tvName.setText(data.get(position).name);
        img.setOnClickListener(this);
        img.setTag(position);
        switch (data.get(position).statu) {
            case 1:
                tvStatu.setText("取件检查");
                tvQS.setVisibility(View.VISIBLE);
                tvDesc.setText("已配送订单：" + data.get(position).time);
                tvBZ.setText("取件骑手备注：衣服少扣  鞋子没有鞋带");
                break;
            case 2:
                tvStatu.setText("商家检查");
                tvQS.setVisibility(View.GONE);
                tvDesc.setText("营业时间：" + data.get(position).time);
                tvBZ.setText("商家备注：衣服少扣  鞋子没有鞋带");
                break;
            case 3:
                tvStatu.setText("送件检查");
                tvQS.setVisibility(View.VISIBLE);
                tvDesc.setText("已配送订单：" + data.get(position).time);
                tvBZ.setText("送件骑手备注：衣服少扣  鞋子没有鞋带");
                break;
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}
