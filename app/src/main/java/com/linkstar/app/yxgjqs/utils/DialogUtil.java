package com.linkstar.app.yxgjqs.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 加载对话框
 *
 * @author hx
 * @date 2018年6月13日
 */
public class DialogUtil {

    /**
     * 正在加载的对话框
     */
    public static Dialog getLoadingDiolog(Context context, String content) {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(content);
        dialog.setCancelable(false);// 点击对话框外部不消失
        return dialog;
    }

    /**
     * 正在加载的对话框
     *
     * @param context
     * @param content    内容
     * @param cencelable 点击外部是否消失
     * @return
     */
    public static Dialog getLoadingDiolog2(Context context, String content, boolean cencelable) {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(content);
        dialog.setCancelable(cencelable);
        return dialog;
    }

    // 列表选择框
    public static AlertDialog.Builder getListChooseDialog(Context context, String[] itemsName,
                                                          DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(itemsName, clickListener);
        return builder;
    }
}
