
package com.linkstar.app.yxgjqs.activity;


import android.os.Bundle;
import android.widget.ImageView;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseCompatActivity;

public class PhotoViewActivity extends BaseCompatActivity {
    private ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        init();
        loadData();
        event();
    }

    private void init() {
        show = (ImageView) this.findViewById(R.id.img_show_photo);
    }

    private void event() {
        setBackClick();

    }

    private void loadData() {

    }

}
