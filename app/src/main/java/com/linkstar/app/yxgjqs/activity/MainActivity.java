package com.linkstar.app.yxgjqs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.adapter.GvHomeMenuAdapter;
import com.linkstar.app.yxgjqs.base.BaseActivity;
import com.linkstar.app.yxgjqs.bean.HomeMenuBean;
import com.linkstar.app.yxgjqs.utils.ConstantUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oragee.banners.BannerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private GridView gridView;
    private ImageView imgChange, imgStatu, imgPseron;
    private TextView tvStatu, tvHot,tvTime;
    private boolean isWork = false;
    private boolean isExit = false;
    private Animation mAnim;
    private List<HomeMenuBean> menu = new ArrayList<>();
    private ImmersionBar mImmersionBar; //沉浸式
    private ImageLoader loder;

    private int[] imgs = {R.drawable.img_banner_bg, R.drawable.img_banner_2};
//    private String[] imgs = {"http://img3.qjy168.com/provide/2014/11/21/5769848_20141121093911.jpg", "http://y3.ifengimg.com/4cd977d1b66e52dc/2014/0307/rdn_531999ac79edf.jpg"};
    private List<View> viewList = new ArrayList<>();
    BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarAlpha(0.2f)
                .statusBarDarkFont(true, 0.2f)
                .init();
        initView();
        event();
        loadData();
    }

    private void initView() {
        loder = ImageLoader.getInstance();
        gridView = (GridView) this.findViewById(R.id.gv_main_page_item);
        imgChange = (ImageView) this.findViewById(R.id.img_change_work_statu);
        imgStatu = (ImageView) this.findViewById(R.id.img_work_statu);
        tvStatu = (TextView) this.findViewById(R.id.tv_work_statu);
        imgPseron = (ImageView) this.findViewById(R.id.img_home_menu_personal);
        tvHot = (TextView) this.findViewById(R.id.tv_hot_pot);
        tvTime = (TextView) this.findViewById(R.id.tv_home_time);
        //禁止右滑关闭窗口
        setSwipeBackEnable(false);

        mAnim = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        mAnim.setInterpolator(interpolator);

        //判断当前是否是工作状态
        if (isWork) {
            imgChange.setImageResource(R.drawable.icon_complete);
        } else {
            imgChange.setImageResource(R.drawable.icon_start);
        }
        //设置当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        tvTime.setText("今日"+time);
    }

    private void event() {
        imgChange.setOnClickListener(this);
        imgPseron.setOnClickListener(this);
        imgStatu.setOnClickListener(this);
        tvHot.setOnClickListener(this);
        gridView.setOnItemClickListener(listener);

    }

    private void loadData() {
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.FIT_XY);
//            loder.displayImage(imgs[i],image);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        bannerView = (BannerView) findViewById(R.id.banner);
        bannerView.startLoop(true);

        bannerView.setViewList(viewList);

        menu.add(new HomeMenuBean(0, R.drawable.icon_order_to_get, "待上门"));
        menu.add(new HomeMenuBean(1, R.drawable.icon_order_not_done, "待完成"));
        menu.add(new HomeMenuBean(2, R.drawable.icon_order_done, "已完成"));
        menu.add(new HomeMenuBean(3, R.drawable.icon_order_cancle, "已取消"));
        menu.add(new HomeMenuBean(4, R.drawable.icon_system_msg, "系统消息"));
        menu.add(new HomeMenuBean(5, R.drawable.icon_share, "推广"));

        gridView.setAdapter(new GvHomeMenuAdapter(this, menu));
        setGridViewHeightBasedOnChildren(gridView,3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_change_work_statu:
                //点击切换工作状态。此处需网络请求
                if (isWork) {
                    imgChange.setImageResource(R.drawable.icon_start);
                    imgStatu.setImageResource(R.drawable.img_close_lintener);
                    tvStatu.setText("休息中");
                    if (null != mAnim) {
                        imgStatu.clearAnimation();  //关闭动画
                    }
                    isWork = false;
                } else {
                    imgChange.setImageResource(R.drawable.icon_complete);
                    imgStatu.setImageResource(R.drawable.img_home_listen_order);
                    tvStatu.setText("去接单");
                    if (mAnim != null) {
                        imgStatu.startAnimation(mAnim);  //开始动画
                    }
                    isWork = true;
                }
                break;
            case R.id.img_home_menu_personal:
                startActivity(PersonalActivity.class);
                break;
            case R.id.img_work_statu:
                if (isWork)
                    startActivity(WaitReceiptActivity.class);
                break;
            case R.id.tv_hot_pot:
                startActivity(HotspotActivity.class);
                break;
        }
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(MainActivity.this, WaitForPickUpActivity.class);
                    intent.putExtra("action_type", ConstantUtil.ACTION_TYPE_PICKUP);
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this, WaitForPickUpActivity.class);
                    intent.putExtra("action_type", ConstantUtil.ACTION_TYPE_SEND);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, CompleteOrderActivity.class);
                    intent.putExtra("action_type", ConstantUtil.ACTION_TYPE_DONE);
                    startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, CompleteOrderActivity.class);
                    intent.putExtra("action_type", ConstantUtil.ACTION_TYPE_CANCLE);
                    startActivity(intent);
                    break;
                case 4:
                    startActivity(MessageActivity.class);
                    break;
                case 5:
                    startActivity(ExtensionActivity.class);
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;// 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态

    }

    /**
     * 动态设置GridView的高度
     */
    public static void setGridViewHeightBasedOnChildren(GridView gridView, int num)
    {
        if (num < 1)
            num = 1;
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }

        int totalHeight = 0;
        int count = listAdapter.getCount();
        count = listAdapter.getCount() % num == 0 ? count / num : count / num + 1;
        for (int i = 0; i < count; i++)
        {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + count * 30;
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();

        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
}
