package com.linkstar.app.yxgjqs.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;

import com.linkstar.app.yxgjqs.R;
import com.linkstar.app.yxgjqs.utils.FileUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.text.DecimalFormat;

public class MyApplication extends Application {
    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;
    public static Context applicationContext;
    private static final String TAG = MyApplication.class.getName();
    Object attached = null;
    private SharedPreferences pref;
    private static final String LOCAL_DATA = TAG + ".localData";

    DecimalFormat df = new DecimalFormat("0.00#");

    public MyApplication() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 全局Context
        applicationContext = getApplicationContext();

        getApplicationContext();
        // 创建下载和缓存文件夹
        FileUtil.createDownloadFolder();

        // 初始化图片加载库
        ImageLoader.getInstance().init(getImageLoaderConfiguration(R.drawable.img_linkstar_blank));
        // 初始化日志库
//		Logger.init("Check");
        pref = getApplicationContext().getSharedPreferences(LOCAL_DATA, Context.MODE_PRIVATE);
    }


    public ImageLoaderConfiguration getImageLoaderConfiguration(int drawableId) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .diskCacheExtraOptions(480, 800, null)
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024).diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).diskCacheFileCount(100)
                // 缓存的文件数量
                // .discCache(
                // new UnlimitedDiscCache(new File(Environment
                // .getExternalStorageDirectory()
                // + "/myApp/imgCache")))
                .diskCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory() + "/linkstar/cache")))

                // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions(drawableId))
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)).writeDebugLogs() // Remove
                .build();// 开始构建
        return config;
    }

    public static DisplayImageOptions getDisplayOptions(int drawableId) {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_linkstar_blank) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.img_linkstar_blank)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.img_linkstar_blank) // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnLoading(drawableId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(drawableId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(drawableId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
//				.displayer(new FadeInBitmapDisplayer(500))// 是否图片加载好后渐入的动画时间(有闪烁)
                .displayer(new SimpleBitmapDisplayer())
                .build();// 构建完成
        return options;
    }

    public static DisplayImageOptions getNoFadeInDisplayOptions(int drawableId) {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_linkstar_blank) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.img_linkstar_blank)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.img_linkstar_blank) // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnLoading(drawableId) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(drawableId)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(drawableId) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                // .delayBeforeLoading(int delayInMillis)//int
                // delayInMillis为你设置的下载前的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .build();// 构建完成
        return options;
    }

    public void setReaderAttached(Object attached) {
        this.attached = attached;
    }

    public Object getReaderAttached() {
        return attached;
    }

    public String format(double value) {
        return df.format(value);
    }

    public void write(String key, String value) {
        synchronized (this) {
            pref.edit().putString(key, value).commit();
        }
    }

    public void write(String key, int value) {
        synchronized (this) {
            pref.edit().putInt(key, value).commit();
        }
    }

    public void write(String key, boolean value) {
        synchronized (this) {
            pref.edit().putBoolean(key, value).commit();
        }
    }

    public String read(String key, String defValue) {
        return pref.getString(key, defValue);
    }

    public int read(String key, int defValue) {
        return pref.getInt(key, defValue);
    }

    public boolean read(String key, boolean defValue) {
        return pref.getBoolean(key, defValue);
    }
}
