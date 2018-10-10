package com.linkstar.app.yxgjqs.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseActivity;

/**
 * 启动页
 */
public class StartPagerActivity extends BaseActivity {
    SharedPreferences preferences;
    private ImmersionBar mImmersionBar; //沉浸式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_pager);

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    // 判断程序是否登录，如果已登录跳转首页否则跳转登录界面
                    is_login = true;
                    if (is_login) {
                        startActivity(MainActivity.class);
                    } else {
                        startActivity(LoginActivity.class);
                    }
                    finish();
                } catch (Exception e) {

                }

                super.run();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

    }
}
