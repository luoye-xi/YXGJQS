package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;

/**
 * Created by hx
 * Time 2018/8/3/003.
 * 设置界面
 */

public class SettingActivity extends BaseSlideActivity implements View.OnClickListener {

    private RelativeLayout layoutSet, layoutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        event();
        loadData();
    }

    private void initView() {
        setBackClick();
        layoutSet = (RelativeLayout) this.findViewById(R.id.rLayout_change_pwd);
        layoutUs = (RelativeLayout) this.findViewById(R.id.rLayout_about_us);
    }

    private void event() {
        layoutSet.setOnClickListener(this);
        layoutUs.setOnClickListener(this);
    }

    private void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rLayout_change_pwd:
                startActivity(SetPwdActivity.class);
                break;
            case R.id.rLayout_about_us:
                startActivity(AboutUsActivity.class);
                break;
        }
    }
}
