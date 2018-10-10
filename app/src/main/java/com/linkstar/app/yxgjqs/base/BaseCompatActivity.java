package com.linkstar.app.yxgjqs.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by hx
 * Time 2018/8/4/004.
 * 个人沉浸右滑页（顶部图片）
 */

public class BaseCompatActivity extends BaseActivity {

    private ImmersionBar mImmersionBar; //沉浸式
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    protected void startActivity(Class<?> cls, Bundle bdl) {
        if (intent == null) {
            intent = new Intent(this, cls);
        }
        intent.setClass(this, cls);
        if (bdl != null) {
            intent.putExtras(bdl);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

}
