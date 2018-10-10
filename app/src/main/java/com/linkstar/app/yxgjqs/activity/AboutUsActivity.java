package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;

/**
 * Created by hx
 * Time 2018/8/3/003.
 */

public class AboutUsActivity extends BaseSlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setBackClick();
    }
}
