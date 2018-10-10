package com.linkstar.app.yxgjqs.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.utils.AppManager;
import com.linkstar.app.yxgjqs.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by hx
 * Time 2018/7/30/030.
 * 支持滑动返回
 * activity基类--设置
 */

public class BaseActivity extends SwipeBackActivity {
    private Intent intent;
    private Toast toast;
    public Context context;
    public static List<Activity> acts = new ArrayList<>();


    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static String user_token = "";
    public static boolean is_change = true;
    public static boolean is_login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        is_login = SPUtil.getLoginState(context);
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
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

    // TOAST
    protected void showShortToast(CharSequence msg) {
        if (null == toast) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    protected void showLongToast(CharSequence msg) {
        if (null == toast) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }

    protected boolean checkLogin() {
        return true;
    }

    /**
     * 为Activity的布局下的id为iv_base_back的控件设置返回事件
     */
    public void setBackClick() {
        View imgBack = this.findViewById(R.id.img_base_back);
        if (null != imgBack) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // 设置过渡动画
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        // 设置过渡动画
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // 设置过渡动画
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        hintKeyBoard();
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
