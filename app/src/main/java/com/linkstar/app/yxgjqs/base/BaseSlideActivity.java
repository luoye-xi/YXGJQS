package com.linkstar.app.yxgjqs.base;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.linkstar.app.yxgjqs.R;

/**
 * Created by hx
 * Time 2018/8/4/004.
 * 普通沉浸式右滑
 */

public class BaseSlideActivity extends BaseActivity {

    private ImmersionBar mImmersionBar; //沉浸式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarAlpha(0.2f)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
}
