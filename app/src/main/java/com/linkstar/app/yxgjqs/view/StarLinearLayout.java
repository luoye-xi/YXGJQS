package com.linkstar.app.yxgjqs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linkstar.app.yxgjqs.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 星星控件
 */
public class StarLinearLayout extends LinearLayout implements OnClickListener {
    /**
     * 星星之间的间距
     */
    private int mMargin = 30;
    /**
     * 是否可点击
     */
    private boolean isEdit;
    /**
     * 初始的值
     */
    private float mScore = 4;

    private List<ImageView> stars = new ArrayList<ImageView>();

    public StarLinearLayout(Context context) {
        this(context, null);
    }

    public StarLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.star);
            mMargin = (int) a.getDimension(R.styleable.star_margin, 10);
            isEdit = a.getBoolean(R.styleable.star_isEdit, false);
            mScore = a.getFloat(R.styleable.star_score, 0);
            a.recycle();
        }
        init();
        setScore(mScore);
    }

    private void init() {
        LayoutParams params = new LayoutParams(64, 64);
        params.weight = 1;
        params.rightMargin = mMargin;
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(getContext());
            star.setImageResource(R.drawable.icon_favorite_orange);
            stars.add(star);
            addView(star, params);

            star.setOnClickListener(this);
        }
    }

    public void setScore(float score) {
        if (score < 0 || score > 5)
            score = 0;
        mScore = score;
        Log.d("TAG", "score:" + score);
        setStar(((int) (10 * score)) / 5);
    }

    public float getScore() {
        return mScore;
    }

    private void setStar(int level) {
        int i;
        Log.d("TAG", "level:" + level);
        for (i = 0; i < stars.size(); i++) {
            stars.get(i).setImageResource(R.drawable.icon_favorite);
        }
        for (i = 0; i < level / 2; i++) {
            stars.get(i).setImageResource(R.drawable.icon_favorite_orange);
        }
        if (level % 2 > 0) {
            stars.get(i).setImageResource(R.drawable.icon_favorite_orange);
            i++;
        }
    }

    ChangeListener changeListener;

    // 为每个接口设置监听器
    public void setChangeListener(ChangeListener change) {
        this.changeListener = change;
    }

    public interface ChangeListener {

        void Change(int level);

    }

    @Override
    public void onClick(View v) {
        if (stars.contains(v)) {
            Log.d("TAG", "isEdit:" + isEdit);
            if (!isEdit)
                return;
            int index = stars.indexOf(v);
            setScore(index + 1);
            changeListener.Change(index + 1);
        }
    }
}
