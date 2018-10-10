package com.linkstar.app.yxgjqs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseActivity;
import com.linkstar.app.yxgjqs.utils.AppManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 印象管家骑手端登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText etPhone, etPwd;
    private TextView tvFind;
    private boolean isExit = false;
    private ImmersionBar mImmersionBar; //沉浸式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        initView();
        event();
        loadData();
    }

    private void initView() {
        btnLogin = (Button) this.findViewById(R.id.btn_to_login);
        etPhone = (EditText) this.findViewById(R.id.et_login_phone);
        etPwd = (EditText) this.findViewById(R.id.et_login_pwd);
        tvFind = (TextView) this.findViewById(R.id.tv_to_find_pwd);
        setSwipeBackEnable(false);
    }


    private void event() {
        btnLogin.setOnClickListener(this);
        tvFind.setOnClickListener(this);
    }

    private void loadData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_to_login:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_to_find_pwd:
                intent = new Intent(this, SetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;// 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            //退出
            AppManager.getAppManager().AppExit(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
