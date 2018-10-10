package com.linkstar.app.yxgjqs.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;

/**
 * Created by hx
 * Time 2018/8/1/001.
 */

public class ExtensionActivity extends BaseSlideActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension);
        TextView tvShare = (TextView) this.findViewById(R.id.tv_to_extension);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultDialog();
            }
        });
        setBackClick();
    }

    private void showResultDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.dialog_share_wx, null);
        builder.setView(v);
        ImageView imgShare = v.findViewById(R.id.img_share_wx_now);

        imgShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShortToast("分享到朋友圈...");
                dialog.dismiss();

            }
        });
        dialog = builder.create();
        dialog.show();
        // 将对话框的大小按屏幕大小的百分比设置
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth() * 0.8); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }
}
