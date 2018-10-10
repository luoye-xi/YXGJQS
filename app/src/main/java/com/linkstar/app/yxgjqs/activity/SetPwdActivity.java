package com.linkstar.app.yxgjqs.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.utils.ConstantUtil;
import com.linkstar.app.yxgjqs.view.CountdownButton;

/**
 * Created by hx
 * Time 2018/8/3/003.
 * 密码修改
 */

public class SetPwdActivity extends BaseSlideActivity implements View.OnClickListener {

    private EditText etPhone, etCode, etPwd;
    private CountdownButton cbCode;
    private Button btnOK;
    private CheckBox imgShow;
    private boolean isShow = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);
        initView();
        event();
        loadData();
    }

    private void initView() {
        etPhone = (EditText) this.findViewById(R.id.et_change_input_phone);
        etCode = (EditText) this.findViewById(R.id.et_change_input_code);
        etPwd = (EditText) this.findViewById(R.id.et_change_input_pwd);
        cbCode = (CountdownButton) this.findViewById(R.id.cb_change_get_code);
        btnOK = (Button) this.findViewById(R.id.btn_change_ok);
        imgShow = (CheckBox) this.findViewById(R.id.cb_show_pwd);
    }

    private void event() {
        setBackClick();
        cbCode.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        imgShow.setOnClickListener(this);
    }

    private void loadData() {

    }

    @Override
    public void onClick(View v) {
        String phone;
        switch (v.getId()) {
            case R.id.cb_change_get_code:
                phone = etPhone.getText().toString();
                // 验证是否输入了合法的手机号
                if (ConstantUtil.checkPhoneNumber(phone) == false) {
                    showShortToast("请输入正确的手机号码");
                    return;
                }
                cbCode.startCountDown();
                etCode.setText("8888");
                break;
            case R.id.btn_change_ok:
                showResultDialog(true, "密码已修改成功，请重新登录");
                break;
            case R.id.cb_show_pwd:
                if (imgShow.isChecked()) {
                    //如果选中，显示密码
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().length());
                } else {
                    //否则隐藏密码
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().length());
                }
                break;
        }
    }

    private void showResultDialog(final boolean success, String msg) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.dialog_title_msg_close, null);
        builder.setView(v);
        TextView tvT = (TextView) v.findViewById(R.id.tv_dialog_title);
        TextView tvMsg = (TextView) v.findViewById(R.id.tv_dialog_msg);
        Button btnOK = (Button) v.findViewById(R.id.btn_dialog_OK);
        tvMsg.setText(msg);
        if (success) {
            tvT.setText("设置成功");
            btnOK.setText("重新登录");
        } else {
            tvT.setText("设置失败");
            btnOK.setText("确定");
        }
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (success) {
                    startActivity(LoginActivity.class);
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }

}
