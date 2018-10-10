package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.view.EditTextWithDel;

/**
 * Created by hx
 * Time 2018/8/4/004.
 * 设置昵称
 */

public class SetNickActivity extends BaseSlideActivity {

    private EditTextWithDel etNick;
    private TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick);
        initView();
        event();
        loadData();
    }

    private void initView() {
        etNick = (EditTextWithDel) this.findViewById(R.id.et_set_nickname);
        tvSave = (TextView) this.findViewById(R.id.tv_save_nickname);
    }

    private void event() {
        setBackClick();
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast(etNick.getText().toString());
                finish();
            }
        });
    }

    private void loadData() {

    }
}
