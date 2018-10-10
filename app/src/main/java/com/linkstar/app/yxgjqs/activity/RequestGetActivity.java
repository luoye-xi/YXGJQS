package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/3/003.
 * 请求签收activity
 */

public class RequestGetActivity extends BaseSlideActivity {

    private TextView tvCode1, tvCode2, tvCode3, tvCode4;
    private EditText etnum;
    private Button btnOK;
    private List<TextView> tvs = new ArrayList<>();
    private int mStatu;
    private RelativeLayout layoutCode;
    private ImageView imgQRCode;
    private TextView tvSendCode;

    private String code = "湖南星链网络科技有限公司-印象管家项目骑手端欢迎您！";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_get);
        initView();
        event();
        loadData();
    }

    private void initView() {
        mStatu = getIntent().getIntExtra("action_statu", 1);
        layoutCode = (RelativeLayout) this.findViewById(R.id.rLayout_request_get_code);
        imgQRCode = (ImageView) this.findViewById(R.id.img_qrcode);
        tvCode1 = (TextView) this.findViewById(R.id.tv_code_1);
        tvCode2 = (TextView) this.findViewById(R.id.tv_code_2);
        tvCode3 = (TextView) this.findViewById(R.id.tv_code_3);
        tvCode4 = (TextView) this.findViewById(R.id.tv_code_4);
        etnum = (EditText) this.findViewById(R.id.et_inputPickupNum);
        btnOK = (Button) this.findViewById(R.id.btn_reuqest_get);
        tvSendCode = (TextView) this.findViewById(R.id.tv_send_code);
        tvs.clear();
        tvs.add(tvCode1);
        tvs.add(tvCode2);
        tvs.add(tvCode3);
        tvs.add(tvCode4);

        if (mStatu==2){
            imgQRCode.setVisibility(View.GONE);
            layoutCode.setVisibility(View.VISIBLE);
        }else{
            imgQRCode.setVisibility(View.VISIBLE);
            layoutCode.setVisibility(View.GONE);
        }

    }

    private void event() {
        setBackClick();
        etnum.addTextChangedListener(watcher);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatu==2){
                    String code = tvCode1.getText().toString() + tvCode2.getText().toString() + tvCode3.getText().toString()
                            + tvCode4.getText().toString();
                    showShortToast(code);
                }else {
                    showShortToast("签收成功！");
                }
                startActivity(MainActivity.class);
            }
        });
        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("验证码发送成功！");
            }
        });
    }

    private void loadData() {
//        Bitmap bitmap = ZXingUtils.createQRImage(code, imgQRCode.getWidth(), imgQRCode.getHeight(), BitmapFactory.decodeResource(getResources(), R.mipmap.img_login_logo), null);
//        imgQRCode.setImageBitmap(bitmap);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for (TextView tvNum : tvs)
                tvNum.setText("");
            String str = s.toString().trim();
            if (!TextUtils.isEmpty(str)) {
                for (int i = 0; i < str.length(); i++) {
                    tvs.get(i).setText(str.substring(i, i + 1));
                }
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
