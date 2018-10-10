package com.linkstar.app.yxgjqs.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.base.BaseSlideActivity;
import com.linkstar.app.yxgjqs.net.NetWork;
import com.linkstar.app.yxgjqs.utils.FileUtil;
import com.linkstar.app.yxgjqs.view.PhotoListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.linkstar.app.yxgjqs.activity.PersonalinfoActivity.hasSdcard;

/**
 * Created by hx
 * Time 2018/8/2/002.
 * 拍照取证界面(确认取货)
 */

public class PhotoEvidenceActivity extends BaseSlideActivity implements View.OnClickListener {

    private int mStatu;
    private TextView tvNot;
    private Button btnConfirm;
    public static PhotoEvidenceActivity instance = null;
    private PhotoListView plv;
    private ImageView imgAdd;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_evidence);
        instance = this;
        acts.add(this);
        initView();
        event();
        loadData();
    }

    private void initView() {
        mStatu = getIntent().getIntExtra("action_statu", 1);
        tvNot = (TextView) this.findViewById(R.id.tv_cloth_not_wash);
        btnConfirm = (Button) this.findViewById(R.id.btn_confirm);
        plv = (PhotoListView) this.findViewById(R.id.plv_photo_list);
        imgAdd = (ImageView) this.findViewById(R.id.img_add_photo);

        if (mStatu == 1) {
            //取件
            tvNot.setVisibility(View.VISIBLE);
            btnConfirm.setText("计价");
        } else {
            //送件
            tvNot.setVisibility(View.GONE);
            btnConfirm.setText("确定");
        }
    }

    private void event() {
        setBackClick();
        tvNot.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        imgAdd.setOnClickListener(this);

        plv.setOnDelectPhotoListener(new PhotoListView.OnAddorDelectPhotoListener() {
            @Override
            public void onAdd(int num) {
                if (num >= 3) {
                    imgAdd.setVisibility(View.GONE);
                } else {
                    imgAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDelete(int num) {
                if (num >= 3) {
                    imgAdd.setVisibility(View.GONE);
                } else {
                    imgAdd.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cloth_not_wash:
                startActivity(NotWashActivity.class);
                break;
            case R.id.btn_confirm:
                if (mStatu == 1) {
                    //取件-->计价
                    startActivity(ValuationActivity.class);
                } else {
                    //送件
                    showShortToast("取件成功");
                    startActivity(MainActivity.class);
                }
                break;
            case R.id.img_add_photo:
                if (ActivityCompat.checkSelfPermission(PhotoEvidenceActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //权限发生了改变 true  //  false 小米
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoEvidenceActivity.this, Manifest.permission.CAMERA)) {
                        new AlertDialog.Builder(PhotoEvidenceActivity.this).setTitle("title")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 请求授权
                                        ActivityCompat.requestPermissions(PhotoEvidenceActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                                    }
                                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
                    } else {
                        ActivityCompat.requestPermissions(PhotoEvidenceActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                } else {
                    take_photo();//已经授权了就调用打开相机的方法
                }
                break;
        }
    }

    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    public static final int TAKE_PHOTO = 1;//启动相机标识

    /**
     * 拍照获取图片
     **/
    public void take_photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            outputImagepath = new File(Environment.getExternalStorageDirectory(), filename + ".png");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                imageUri = Uri.fromFile(outputImagepath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, outputImagepath.getAbsolutePath());
                imageUri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //打开相机后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    /**
                     * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                     */
                    displayImage(outputImagepath.getAbsolutePath());
                    Log.i("tag", "拍照图片路径>>>>" + outputImagepath);
                }

                String path = FileUtil.getRealFilePath(this, imageUri);
                File flie = new File(path);
                String url = "http://39.104.69.120:9000/renren-fast/app/file/upload";
                NetWork.httpFilePost(url,flie,400,400,request);
                break;
            default:
                break;
        }
    }

    /**
     * 拍完照和从相册获取玩图片都要执行的方法(根据图片路径显示图片)
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            //orc_bitmap = BitmapFactory.decodeFile(imagePath);//获取图片
            orc_bitmap = comp(BitmapFactory.decodeFile(imagePath)); //压缩图片
            ImgUpdateDirection(imagePath);//显示图片,并且判断图片显示的方向,如果不正就放正
        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_LONG).show();
        }
    }

    //比例压缩
    private Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;//压缩好比例大小后再进行质量压缩
    }

    //改变拍完照后图片方向不正的问题
    private void ImgUpdateDirection(String filepath) {
        int digree = 0;//图片旋转的角度
        //根据图片的URI获取图片的绝对路径
        Log.i("tag", ">>>>>>>>>>>>>开始");
        //String filepath = ImgUriDoString.getRealFilePath(getApplicationContext(), uri);
        Log.i("tag", "》》》》》》》》》》》》》》》" + filepath);
        //根据图片的filepath获取到一个ExifInterface的对象
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
            Log.i("tag", "exif》》》》》》》》》》》》》》》" + exif);
            if (exif != null) {

                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;

                }

            }
            //如果图片不为0
            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                orc_bitmap = Bitmap.createBitmap(orc_bitmap, 0, 0, orc_bitmap.getWidth(),
                        orc_bitmap.getHeight(), m, true);
            }
            if (orc_bitmap != null) {
                plv.addPhoto(orc_bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
    }

    private RequestCallBack<String> request = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.d("TAG",responseInfo.result);
            showShortToast(responseInfo.result);
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };
}
