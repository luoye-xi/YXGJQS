package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.view.FlowViewGroup;
import com.linkstar.app.yxgjqs.view.StarLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/8/3/003.
 * 评价页面
 */

public class EvaluateActivity extends BaseSlideActivity {

    private FlowViewGroup fvgStore;
    private StarLinearLayout starStroe;
    private List<String> flagStore = new ArrayList<>();

    private FlowViewGroup fvgUser;
    private StarLinearLayout starUser;
    private List<String> flagUser = new ArrayList<>();

    private Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initView();
        event();
        loadData();
    }

    private void initView() {
        fvgStore = (FlowViewGroup) this.findViewById(R.id.fvg_evaluate_store);
        fvgUser = (FlowViewGroup) this.findViewById(R.id.fvg_evaluate_user);

        starStroe = (StarLinearLayout) this.findViewById(R.id.sll_star_store);
        starUser = (StarLinearLayout) this.findViewById(R.id.sll_star_user);
        btnOK = (Button) this.findViewById(R.id.btn_evaluate_confirm);

        starStroe.setScore((float) 5);
        starUser.setScore((float) 5);
    }

    private void event() {
        setBackClick();
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortToast("评论成功！");
                finish();
            }
        });

        starStroe.setChangeListener(new StarLinearLayout.ChangeListener()
        {
            @Override
            public void Change(int level)
            {
                showShortToast("您给了商家" + level + "分");
            }
        });

        starUser.setChangeListener(new StarLinearLayout.ChangeListener()
        {
            @Override
            public void Change(int level)
            {
                showShortToast("您给了用户" + level + "分");
            }
        });
    }

    private void loadData() {

        flagStore.add("专业");
        flagStore.add("热忱");
        flagStore.add("装备专业");
        flagStore.add("态度好");
        flagStore.add("环境好");
        flagStore.add("洗衣速度快");
        initStoreViewGroup(fvgStore, flagStore);


        flagUser.add("热情");
        flagUser.add("好沟通");
        flagUser.add("态度好");
        initStoreViewGroup(fvgUser, flagUser);
    }

    /**
     * 初始化评论标签流式布局并赋值
     *
     * @param fvg
     * @param data
     */
    private void initStoreViewGroup(FlowViewGroup fvg, List<String> data) {
        TextView tv;
        fvg.removeAllViews();
        if (data.size() == 0) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            final String str = data.get(i);
            tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_flow_gods_detail, fvg, false);
            tv.setText(str);
            tv.setSelected(false);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isSelected()){
                        v.setSelected(false);
                        v.setBackgroundResource(R.drawable.back_gray_line_coner);
                        TextView view = (TextView) v;
                        view.setTextColor(getResources().getColor(R.color.text_gray));
                    }else{
                        v.setSelected(true);
                        v.setBackgroundResource(R.drawable.background_blue_5dp);
                        TextView view = (TextView) v;
                        view.setTextColor(getResources().getColor(R.color.white));
                    }
                    showShortToast(str);
                }
            });
            fvg.addView(tv);
        }
    }
}
