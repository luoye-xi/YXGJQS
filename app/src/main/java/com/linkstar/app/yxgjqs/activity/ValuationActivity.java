package com.linkstar.app.yxgjqs.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.ColthListAdapter;
import com.linkstar.app.yxgjqs.adapter.TransportInfoAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.bean.ClothBean;
import com.linkstar.app.yxgjqs.bean.TransportBean;
import com.linkstar.app.yxgjqs.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/2/002.
 * 待上门确认取货计价activity
 */

public class ValuationActivity extends BaseSlideActivity implements View.OnClickListener ,TransportInfoAdapter.Callback{

    private TextView tvPay, tvChange;
    private ListView lvCloth, lvInfos;
    private List<ClothBean> data = new ArrayList<>();
    private List<TransportBean> transData = new ArrayList();
    private final int PERMS_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valuation);
        acts.add(this);
        initView();
        event();
        loadData();

    }

    private void initView() {

        tvPay = (TextView) this.findViewById(R.id.tv_request_pay);
        lvCloth = (ListView) this.findViewById(R.id.lv_valuation_pay_list);
        tvChange = (TextView) this.findViewById(R.id.tv_change_order);
        lvInfos = (ListView) this.findViewById(R.id.lv_order_valuate_infos);
    }

    private void event() {
        setBackClick();
        tvPay.setOnClickListener(this);
        tvChange.setOnClickListener(this);
    }

    private void loadData() {
        data.add(new ClothBean(R.drawable.test_cloth_1, "洗衣/上装洗护", "衬衣/T恤", 5, "23.20"));
        data.add(new ClothBean(R.drawable.test_cloth_2, "洗衣/上装洗护", "衬衣/T恤", 2, "88.80"));
        lvCloth.setAdapter(new ColthListAdapter(this, data));
        setListViewHeightBasedOnChildren(lvCloth, 0);

        transData.add(new TransportBean(1, R.drawable.rest_icon_personal_1, "隔壁老六", "1914   好评率：100%"));
        lvInfos.setAdapter(new TransportInfoAdapter(this, transData,this));
        setListViewHeightBasedOnChildren(lvInfos, 20);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_request_pay:
                Intent intent = new Intent(this,WaitForPickUpActivity.class);
                intent.putExtra("action_type", ConstantUtil.ACTION_TYPE_PICKUP);
                startActivity(intent);
                for (Activity activity:acts){
                    activity.finish();
                }
                break;
            case R.id.tv_change_order:
                startActivity(ChangePriceActivity.class);
                break;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, int add) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + add;

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;

        listView.setLayoutParams(params);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private AlertDialog dialog;//拨打电话
    @Override
    public void click(View v) {
        int position = (Integer) v.getTag();
        showLinkDialog(transData.get(position).name);
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
