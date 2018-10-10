package com.linkstar.app.yxgjqs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx
 * Time 2018/7/31/031.
 */

public class LvQuestionAdapter extends BaseAdapter {

    private Context context;
    private List<QuestionBean> data = new ArrayList<>();

    public LvQuestionAdapter(Context context, List<QuestionBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lv_question,null);
        TextView tvTitle = v.findViewById(R.id.tv_question_title);
        TextView tvContent = v.findViewById(R.id.tv_question_content);

        tvTitle.setText(data.get(position).title);
        tvContent.setText(data.get(position).content);
        return v;
    }
}
