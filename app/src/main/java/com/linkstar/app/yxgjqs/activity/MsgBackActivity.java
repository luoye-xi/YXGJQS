package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;

public class MsgBackActivity extends BaseSlideActivity implements View.OnClickListener {

    private EditText etMsg;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_back);
        initView();
        event();
        loadData();
    }

    private void initView() {
        etMsg = (EditText) this.findViewById(R.id.et_msg_back);
        btnSubmit = (Button) this.findViewById(R.id.btn_updata_msg);
    }

    private void event() {
        setBackClick();
        btnSubmit.setOnClickListener(this);
    }

    private void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_updata_msg:
                if (null!=etMsg.getText()){
                    showShortToast(etMsg.getText().toString());
                }

                break;
        }
    }
}
