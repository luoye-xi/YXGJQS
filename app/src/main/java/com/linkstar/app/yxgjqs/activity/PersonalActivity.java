package com.linkstar.app.yxgjqs.activity;

import android.Manifest;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseCompatActivity;
import com.linkstar.app.yxgjqs.utils.AppManager;

public class PersonalActivity extends BaseCompatActivity implements View.OnClickListener {

    private Button btnOut;
    private ImageView imgSetting;
    private RelativeLayout layoutData, layoutKF, layoutQuestion, layoutMsgBack;
    private AlertDialog dialog;
    private LinearLayout layoutEdit;
    private ImageView imgBack;
    private final int PERMS_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
        event();
        loadData();
    }

    private void initView() {
        imgBack = (ImageView) this.findViewById(R.id.img_base_back_personal);
        btnOut = (Button) this.findViewById(R.id.btn_exit_login);
        imgSetting = (ImageView) this.findViewById(R.id.img_personal_setting);
        layoutData = (RelativeLayout) this.findViewById(R.id.rLayout_my_data);
        layoutKF = (RelativeLayout) this.findViewById(R.id.rLayout_link_kf);
        layoutQuestion = (RelativeLayout) this.findViewById(R.id.rLayout_question);
        layoutMsgBack = (RelativeLayout) this.findViewById(R.id.rLayout_msg_back);
        layoutEdit = (LinearLayout) this.findViewById(R.id.lLayout_edit_info);
    }

    private void event() {
        imgBack.setOnClickListener(this);
        btnOut.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        layoutData.setOnClickListener(this);
        layoutKF.setOnClickListener(this);
        layoutQuestion.setOnClickListener(this);
        layoutMsgBack.setOnClickListener(this);
        layoutEdit.setOnClickListener(this);
    }

    private void loadData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_exit_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.img_personal_setting:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rLayout_my_data:
                //我的业绩
                intent = new Intent(this, MyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.rLayout_link_kf:
                showResultDialog();
                break;
            case R.id.rLayout_question:
                //常见问题界面
                intent = new Intent(this, QuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.rLayout_msg_back:
                //设置界面
                intent = new Intent(this, MsgBackActivity.class);
                startActivity(intent);
                break;
            case R.id.lLayout_edit_info:
                intent = new Intent(this, PersonalinfoActivity.class);
                startActivity(intent);
                break;
            case R.id.img_base_back_personal:
                finish();
                break;
        }
    }


    private void showResultDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = inflater.inflate(R.layout.dialog_link_kf, null);
        builder.setView(v);

        Button btnOK = v.findViewById(R.id.btn_dialog_link);
        Button btnCancle = v.findViewById(R.id.btn_dialog_cancle);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
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
