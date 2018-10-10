package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.LvMyRecodAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.bean.MyRecordBean;
import com.linkstar.app.yxgjqs.view.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hx
 * Time 2018/7/31/031.
 * 我的业绩界面
 */

public class MyRecordActivity extends BaseSlideActivity {

    private List<MyRecordBean> data = new ArrayList<>();
    private GridView gvContent;
    private TextView tvTime;
    private CustomDatePicker datePicker;// 日期选择器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_record);
        initView();
        event();
        loadData();
    }

    private void initView() {
        gvContent = (GridView) this.findViewById(R.id.gv_my_record);
        tvTime = (TextView) this.findViewById(R.id.tv_recod_time);
    }

    private void event() {
        setBackClick();
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == datePicker) {
                    initDatePicker();
                }
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                String now1 = sdf1.format(new Date());
                datePicker.show(now1.split(" ")[0]);
            }
        });
    }

    private void loadData() {
        data.add(new MyRecordBean("15", "累计完成（单）"));
        data.add(new MyRecordBean("15", "取件单"));
        data.add(new MyRecordBean("10", "送件单"));
        data.add(new MyRecordBean("5", "按时履约单"));
        data.add(new MyRecordBean("10", "超时次数"));
        data.add(new MyRecordBean("1", "拒单量"));
        data.add(new MyRecordBean("5", "邀请好友数"));
        data.add(new MyRecordBean("", ""));
        data.add(new MyRecordBean("", ""));
        gvContent.setAdapter(new LvMyRecodAdapter(this, data));
    }

    /**
     * 初始化日期选择器
     */
    private void initDatePicker() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月", Locale.CHINA);
        String now = sdf.format(new Date());

        datePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                Date parse = null;
                try {
                    parse = sdf.parse(time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String formatTime = sdf1.format(parse);
                tvTime.setText(formatTime);
            }
        }, "2017-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        datePicker.showMonthTime(true);//只显示年月
        datePicker.setIsLoop(true); // 允许循环滚动

    }
}
