package com.linkstar.app.yxgjqs.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.LvPickUpAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.bean.PickUpBean;
import com.linkstar.app.yxgjqs.utils.ConstantUtil;
import com.linkstar.app.yxgjqs.view.BottomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/7/31/031.
 * 待上门界面
 */

public class WaitForPickUpActivity extends BaseSlideActivity implements View.OnClickListener, LvPickUpAdapter.Callback {

    private ListView lvData;
    private List<PickUpBean> data = new ArrayList<>();
    private List<PickUpBean> data1 = new ArrayList<>();
    private List<PickUpBean> data2 = new ArrayList<>();
    private TextView tvTitle, tvPick, tvSend;
    private String mAction;
    private BottomDialog dialogChooseMap;
    private int position;
    private AlertDialog dialog;//拨打电话
    private final int PERMS_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_pick_up);
        acts.add(this);
        initView();
        event();
        loadData();
    }

    private void initView() {
        mAction = getIntent().getStringExtra("action_type");
        lvData = (ListView) this.findViewById(R.id.lv_wait_pick_up);
        tvTitle = (TextView) this.findViewById(R.id.tv_wait_pick_up_title);
        tvPick = (TextView) this.findViewById(R.id.tv_statu_wait_pick);
        tvSend = (TextView) this.findViewById(R.id.tv_statu_wait_send);
    }

    private void event() {
        setBackClick();
        tvPick.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        lvData.setOnItemClickListener(listener);
    }

    private void loadData() {
        data.add(new PickUpBean(1, 1, 1, "17:00", "隔壁老六", "赵海波", "湘雅附中(开福区湘雅路78号)", "天心区保利国际B1栋2321"));
        data.add(new PickUpBean(1, 2, 1, "17:00", "隔壁老六", "赵海波", "湘雅附中(开福区湘雅路78号)", "天心区保利国际B1栋2321"));
        data.add(new PickUpBean(1, 2, 3, "17:00", "隔壁老六", "赵海波", "湘雅附中(开福区湘雅路78号)", "天心区保利国际B1栋2321"));
        data.add(new PickUpBean(1, 1, 2, "50", "隔壁老六", "赵海波", "湘雅附中(开福区湘雅路78号)", "天心区保利国际B1栋2321"));
        data.add(new PickUpBean(1, 2, 2, "120", "隔壁老六", "赵海波", "湘雅附中(开福区湘雅路78号)", "天心区保利国际B1栋2321"));
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).type == 2) {
                data2.add(data.get(i));
            } else {
                data1.add(data.get(i));
            }
        }

        if (mAction.equals(ConstantUtil.ACTION_TYPE_PICKUP)) {
            tvTitle.setText("待上门");
            lvData.setAdapter(new LvPickUpAdapter(this, data1, this));
            setButtonStatu(true);
        } else if (mAction.equals(ConstantUtil.ACTION_TYPE_SEND)) {
            tvTitle.setText("待完成");
            lvData.setAdapter(new LvPickUpAdapter(this, data2, this));
            setButtonStatu(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_statu_wait_pick:
                tvTitle.setText("待上门");
                mAction = ConstantUtil.ACTION_TYPE_PICKUP;
                lvData.setAdapter(new LvPickUpAdapter(this, data1, this));
                setButtonStatu(true);
                break;
            case R.id.tv_statu_wait_send:
                tvTitle.setText("待完成");
                mAction = ConstantUtil.ACTION_TYPE_SEND;
                lvData.setAdapter(new LvPickUpAdapter(this, data2, this));
                setButtonStatu(false);
                break;
            case R.id.tv_baidu:
                if (isAvilible(this, "com.baidu.BaiduMap"))
                {// 是否安装了百度地图
                    Intent intent2 = new Intent();
                    intent2.setData(Uri.parse("baidumap://map/navi?location=" + 28.173638 + "," + 112.974609 + ""));
                    startActivity(intent2);
                } else
                {
                    showShortToast("未安装百度地图");
                    // 询问用户是否跳转百度地图下载页面
                }
                break;
            case R.id.tv_gaode:
                if (isAvilible(this, "com.autonavi.minimap"))
                {// 是否安装了高德地图
                } else
                {
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

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int statu = 1;
            Intent intent = new Intent(WaitForPickUpActivity.this, OrderDetailsActivity.class);
            if (mAction.equals(ConstantUtil.ACTION_TYPE_PICKUP)) {
                statu = data1.get(position).statu;
            } else if (mAction.equals(ConstantUtil.ACTION_TYPE_SEND)) {
                statu = data2.get(position).statu;
            }
            intent.putExtra("action_type", mAction);
            intent.putExtra("action_statu", statu);
            startActivity(intent);
        }
    };

    private void setButtonStatu(boolean isGet) {
        Drawable drawable;
        //当前是否为上门
        if (isGet) {
            tvPick.setBackgroundResource(R.drawable.img_circular_bule);
            tvPick.setTextColor(getResources().getColor(R.color.white));
            drawable = getResources().getDrawable(R.drawable.img_pick_up_home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
            tvPick.setCompoundDrawables(null, drawable, null, null);
            tvSend.setBackgroundResource(R.drawable.img_circle_blue);
            tvSend.setTextColor(getResources().getColor(R.color.main_color_blue));
            drawable = getResources().getDrawable(R.drawable.img_wait_complet);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
            tvSend.setCompoundDrawables(null, drawable, null, null);
        } else {
            tvPick.setBackgroundResource(R.drawable.img_circle_blue);
            tvPick.setTextColor(getResources().getColor(R.color.main_color_blue));
            drawable = getResources().getDrawable(R.drawable.img_pick_up_home_blue);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
            tvPick.setCompoundDrawables(null, drawable, null, null);
            tvSend.setBackgroundResource(R.drawable.img_circular_bule);
            tvSend.setTextColor(getResources().getColor(R.color.white));
            drawable = getResources().getDrawable(R.drawable.img_wait_complet_white);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
            tvSend.setCompoundDrawables(null, drawable, null, null);
        }
    }

    @Override
    public void click(View v) {
        position = (Integer) v.getTag();
        switch (v.getId()){
            case R.id.btn_to_way:
                startNavigation();
                break;
            case R.id.img_wait_pick_phone:
                showLinkDialog("隔壁老六");
                break;
            case R.id.img_wait_pick_address:
                startNavigation();
                break;
            case R.id.img_wait_send_phone:
                showLinkDialog("赵海波");
                break;
            case R.id.img_wait_send_address:
                startNavigation();
                break;
        }

    }

    /**
     * 设备中是否存在某个app
     * @param context
     * @param packageName
     * @return
     */
    private boolean isAvilible(Context context, String packageName)
    {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++)
        {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    private void startNavigation(){
        dialogChooseMap = BottomDialog.create(getSupportFragmentManager());
        dialogChooseMap.setViewListener(new BottomDialog.ViewListener() { // 可以进行一些必要对View的操作
            @Override
            public void bindView(View v) {
                TextView tvBaiDu = v.findViewById(R.id.tv_baidu);
                TextView tvGaoDe = v.findViewById(R.id.tv_gaode);
                TextView tvCancel = v.findViewById(R.id.tv_cancel);
                tvBaiDu.setOnClickListener(WaitForPickUpActivity.this);
                tvGaoDe.setOnClickListener(WaitForPickUpActivity.this);
                tvCancel.setOnClickListener(WaitForPickUpActivity.this);
            }
        }).setLayoutRes(R.layout.dialog_choose_map).setDimAmount(0.1f) // Dialog window 背景色深度范围：0到1，默认是0.2f
                .setCancelOutside(true) // 点击外部区域是否关闭，默认true
                .show();
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
        TextView tvName= v.findViewById(R.id.tv_link_who);
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
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum){
        //检查拨打电话权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        }
    }

    /**
     * 获取权限回调方法
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
