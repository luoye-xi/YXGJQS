package com.linkstar.app.yxgjqs.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.LvQuestionAdapter;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends BaseSlideActivity {

    private ListView lvQuestion;
private List<QuestionBean> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();
        event();
        loadData();
    }

    private void initView() {
        lvQuestion = (ListView) this.findViewById(R.id.lv_personal_questions);
    }

    private void event() {
        setBackClick();
    }

    private void loadData() {
        data.add(new QuestionBean("阿里巴巴中国市场的诚信体系主要包括","阿里巴巴作为全球最大的网上贸易市场，拥有1530万注册商人会员（数据截止2006年6月，中文网站1284万，英文网站246万），是一个极为注重诚信的市场，诚信的商人在此可以获得商业机会达成交易，不诚信的商人和不诚信的行为会被坚决打击、曝光和取缔！"));
        data.add(new QuestionBean("什么是诚信通档案？包含哪些内容？","通过企业身份认证的阿里巴巴会员，都可以享受诚信通服务获得一个网上信用活档案——诚信通档案。"));

        lvQuestion.setAdapter(new LvQuestionAdapter(this,data));

    }
}
