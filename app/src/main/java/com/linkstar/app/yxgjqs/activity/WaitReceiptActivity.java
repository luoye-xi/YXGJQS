package com.linkstar.app.yxgjqs.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.LvReceiptAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.view.BottomDialog;

import java.util.List;

/**
 * Created by hx
 * Time 2018/8/1/001.
 * 待接单activity
 */

public class WaitReceiptActivity extends BaseSlideActivity implements View.OnClickListener {

    private ListView lvContent;
    private BottomDialog chooseResonDialog;
    private BottomDialog dialogChooseMap;//选择导航
    private AlertDialog dialog;//拨打电话
    private final int PERMS_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_receipt);
        initView();
        event();
        loadData();
    }

    private void initView() {
        lvContent = (ListView) this.findViewById(R.id.lv_wait_receipt);
    }

    private void event() {
        setBackClick();
    }

    private void loadData() {
        lvContent.setAdapter(new LvReceiptAdapter(this, mListener));
    }

    /**
     * 实现类，响应按钮点击事件
     */
    private LvReceiptAdapter.MyClickListener mListener = new LvReceiptAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            switch (v.getId()) {
                case R.id.img_re_order_pick_phone:
                    showLinkDialog("隔壁老六");
                    break;
                case R.id.img_re_order_pick_address:
                    startNavigation();
                    break;
                case R.id.img_re_order_send_phone:
                    showLinkDialog("赵海波");
                    break;
                case R.id.img_re_order_send_address:
                    startNavigation();
                    break;
                case R.id.btn_refuse_order:
                    showResonDialog();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose_reson_busy:
                //当前时段已有订单
                showShortToast("当前时段已有订单");
                if (null != chooseResonDialog)
                    chooseResonDialog.dismiss();
                break;
            case R.id.tv_choose_reson_rest:
                //收工拒单
                showShortToast("收工");
                if (null != chooseResonDialog)
                    chooseResonDialog.dismiss();
                break;
            case R.id.tv_choose_reson_other:
                //其他
                showShortToast("其他");
                if (null != chooseResonDialog)
                    chooseResonDialog.dismiss();
                break;
            case R.id.tv_choose_reson_cancle:
                //取消
                if (null != chooseResonDialog)
                    chooseResonDialog.dismiss();
                break;
            case R.id.tv_baidu:
                if (isAvilible(this, "com.baidu.BaiduMap")) {// 是否安装了百度地图
                    Intent intent2 = new Intent();
                    intent2.setData(Uri.parse("baidumap://map/navi?location=" + 28.173638 + "," + 112.974609 + ""));
                    startActivity(intent2);
                } else {
                    showShortToast("未安装百度地图");
                    // 询问用户是否跳转百度地图下载页面
                }
                break;
            case R.id.tv_gaode:
                if (isAvilible(this, "com.autonavi.minimap")) {
                    // 是否安装了高德地图
                } else {
                    showShortToast("未安装高德地图");
                    // 询问用户是否跳转高德地图下载页面
                }
                break;
            case R.id.tv_cancel:
                if (null != dialogChooseMap)
                    dialogChooseMap.dismiss();
                break;
        }
    }

    private void showResonDialog() {
        // 点击选择拒单原因
        chooseResonDialog = BottomDialog.create(getSupportFragmentManager());
        chooseResonDialog.setViewListener(new BottomDialog.ViewListener() { // 可以进行一些必要对View的操作
            @Override
            public void bindView(View v) {
                TextView tvBusy = v.findViewById(R.id.tv_choose_reson_busy);
                TextView tvRest = v.findViewById(R.id.tv_choose_reson_rest);
                TextView tvOther = v.findViewById(R.id.tv_choose_reson_other);
                TextView tvCancel = v.findViewById(R.id.tv_choose_reson_cancle);
                tvBusy.setOnClickListener(WaitReceiptActivity.this);
                tvRest.setOnClickListener(WaitReceiptActivity.this);
                tvOther.setOnClickListener(WaitReceiptActivity.this);
                tvCancel.setOnClickListener(WaitReceiptActivity.this);
            }
        }).setLayoutRes(R.layout.dialog_choose_reson).setDimAmount(0.1f) // Dialog window 背景色深度范围：0到1，默认是0.2f
                .setCancelOutside(true) // 点击外部区域是否关闭，默认true
                .show();
    }

    private void startNavigation() {
        dialogChooseMap = BottomDialog.create(getSupportFragmentManager());
        dialogChooseMap.setViewListener(new BottomDialog.ViewListener() { // 可以进行一些必要对View的操作
            @Override
            public void bindView(View v) {
                TextView tvBaiDu = v.findViewById(R.id.tv_baidu);
                TextView tvGaoDe = v.findViewById(R.id.tv_gaode);
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvBaiDu.setOnClickListener(WaitReceiptActivity.this);
                tvGaoDe.setOnClickListener(WaitReceiptActivity.this);
                tvCancel.setOnClickListener(WaitReceiptActivity.this);
            }
        }).setLayoutRes(R.layout.dialog_choose_map).setDimAmount(0.1f) // Dialog window 背景色深度范围：0到1，默认是0.2f
                .setCancelOutside(true) // 点击外部区域是否关闭，默认true
                .show();
    }

    /**
     * 设备中是否存在某个app
     *
     * @param context
     * @param packageName
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 拨号对话框
     */
    private void showLinkDialog(String name) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.dialog_link_other, null);
        builder.setView(v);

        Button btnOK = v.findViewById(R.id.btn_dialog_link_other);
        Button btnCancle = v.findViewById(R.id.btn_dialog_link_other_cancle);
        TextView tvName = v.findViewById(R.id.tv_link_who);
        tvName.setText(name);
        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestPermission();
                dialog.dismiss();

            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();

        // 将对话框的大小按屏幕大小的百分比设置
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.8); //设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    private void requestPermission() {

        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                // 返回值：
                //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show();
                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMS_REQUEST_CODE);
            }
        } else {
            callPhone("13723865812");
        }
    }


    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        //检查拨打电话权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        }
    }

    /**
     * 获取权限回调方法
     *
     * @param permsRequestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case PERMS_REQUEST_CODE:
                boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (storageAccepted) {
                    callPhone("13723865812");
                } else {
                    showShortToast("请前往设置权限！");
                }
                break;
        }
    }
}
