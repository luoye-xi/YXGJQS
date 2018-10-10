package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.LvMsgAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseSlideActivity {

    private ListView lvContent;
    private LinearLayout layoutEmpty;
    private List<MessageBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        loadData();
    }

    private void initView() {
        setBackClick();
        lvContent = (ListView) this.findViewById(R.id.lv_msg_content);
        layoutEmpty = (LinearLayout) this.findViewById(R.id.lLayout_msg_empty);
    }

    private void loadData() {
        data.add(new MessageBean("今天 09:05", "今日福利", "今日完成20单后奖励翻倍哦~"));
        data.add(new MessageBean("2018-06-09 22:22", "定时奖励", "今日12:00-14:00完成任务奖励茶水费50元，赶紧接单吧~"));


        lvContent.setAdapter(new LvMsgAdapter(this, data));
        if (null == data || data.size() == 0) {
            layoutEmpty.setVisibility(View.VISIBLE);
        }

    }
}
