package com.linkstar.app.yxgjqs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.LvCompleteOrderAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.bean.CompleteOrderBean;
import com.linkstar.app.yxgjqs.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/1/001.
 * 已完成/已取消订单activity
 */

public class CompleteOrderActivity extends BaseSlideActivity {

    private String mAction;
    private TextView tvTitle;
    private ListView lvContent;
    private List<CompleteOrderBean> data1 = new ArrayList<>();
    private List<CompleteOrderBean> data2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        initView();
        event();
        loadData();
    }

    private void initView() {
        mAction = getIntent().getStringExtra("action_type");
        tvTitle = (TextView) this.findViewById(R.id.tv_complete_order_title);
        lvContent = (ListView) this.findViewById(R.id.lv_complete_order);

    }

    private void event() {
        setBackClick();
        lvContent.setOnItemClickListener(listener);
    }

    private void loadData() {

        data1.add(new CompleteOrderBean(0,1,1,1,"2018-01-02 10:50","隔壁老六","赵海波","湘雅附中(开福区湘雅路78号)","天心区保利国际B1栋2321",null));
        data1.add(new CompleteOrderBean(1,2,1,2,"2018-05-20 09:00","隔壁老六","赵海波","湘雅附中(开福区湘雅路78号)","天心区保利国际B1栋2321",null));
        data1.add(new CompleteOrderBean(2,1,1,2,"2018-08-01 14:28","隔壁老六","赵海波","湘雅附中(开福区湘雅路78号)","天心区保利国际B1栋2321",null));

        data2.add(new CompleteOrderBean(0,1,2,1,"2018-08-02 10:50","隔壁老六","赵海波","湘雅附中(开福区湘雅路78号)","天心区保利国际B1栋2321","客户取消"));
        data2.add(new CompleteOrderBean(1,1,2,1,"2018-08-02 10:50","隔壁老六","赵海波","湘雅附中(开福区湘雅路78号)","天心区保利国际B1栋2321","当前已接单"));

        if (mAction.equals(ConstantUtil.ACTION_TYPE_DONE)) {
            tvTitle.setText("已完成");
            lvContent.setAdapter(new LvCompleteOrderAdapter(this,data1));
        } else if (mAction.equals(ConstantUtil.ACTION_TYPE_CANCLE)) {
            tvTitle.setText("已取消");
            lvContent.setAdapter(new LvCompleteOrderAdapter(this,data2));
        }
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mAction.equals(ConstantUtil.ACTION_TYPE_DONE)){
                Intent intent = new Intent(CompleteOrderActivity.this,OrderDetailsActivity.class);
                intent.putExtra("action_type",ConstantUtil.ACTION_TYPE_DONE);
                intent.putExtra("action_statu",data1.get(position).statu);
                intent.putExtra("action_evaluate",data1.get(position).isEvaluate);
                startActivity(intent);
            }
        }
    };
}
