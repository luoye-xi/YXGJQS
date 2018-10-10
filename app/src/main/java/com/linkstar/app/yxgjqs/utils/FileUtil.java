package com.linkstar.app.yxgjqs.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil
{

	public static boolean deleteFile(String path)
	{
		File file = new File(path);
		if (file.exists())
		{
			return new File(path).delete();
		} else
		{
			return false;
		}

	}

	/**
	 * 启动安装APP的界面，传入APP的URL
	 * 
	 * @param context
	 * @param AppURL
	 * @return true代表启动成功，false代码启动失败
	 */
	public static boolean install(Context context, String AppURL)
	{
		File installFile = new File(AppURL);
		if (installFile.exists())
		{
			if (installFile.getName().contains(".apk"))
			{
				Log.d("Check", "app_url:" + AppURL);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(installFile), "application/vnd.android.package-archive");
				context.startActivity(intent);
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据包名跳转到指定的APP
	 * 
	 * @param context
	 *            上下文
	 * @param packageName
	 *            包名
	 * @return true设备内有此APP false设备内无此APP
	 */
	public static boolean openApp(Context context, String packageName)
	{
		if (isPkgInstalled(context.getPackageManager(), packageName))
		{
			PackageManager pm = context.getPackageManager();
			Intent intent = pm.getLaunchIntentForPackage(packageName);
			if (null == intent)
			{
				return false;
			} else
			{
				context.startActivity(intent);
				return true;
			}
		} else
		{
			return false;
		}

	}

	// 获取文件夹所有文件的大小
	public static long getFolderSize(File file)
	{

		long size = 0;
		try
		{
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++)
			{
				if (fileList[i].isDirectory())
				{
					size = size + getFolderSize(fileList[i]);

				} else
				{
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		// return size/1048576;
		return size;
	}

	// 删除指定目录下文件及目录
	public static void deleteFolderFile(String filePath, boolean deleteThisPath)
	{
		if (!TextUtils.isEmpty(filePath))
		{
			try
			{
				File file = new File(filePath);
				if (file.isDirectory())
				{// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++)
					{
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath)
				{
					if (!file.isDirectory())
					{// 如果是文件，删除
						file.delete();
					} else
					{// 目录
						if (file.listFiles().length == 0)
						{// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// 保存Bitmap图片到指定文件夹中的方法
	public static File saveBitmap(String picName, Bitmap bm)
	{
		File f = new File(URLUtil.CACHE_CATALOG, picName);
		if (f.exists())
		{
			f.delete();
		}
		try
		{
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			return f;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 降低Bitmap质量，减少Bitmap体积
	 */
	public static Bitmap compressImage(Bitmap image)
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100)
		{ // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 按比例降低本地图片分辨率，并降低质量，减少图片体积并转换成Bitmap
	 */
	public static Bitmap getimage(String srcPath)
	{
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		// float hh = 800f;//这里设置高度为800f
		// float ww = 480f;//这里设置宽度为480f
		float hh = 400f;// 这里设置高度为800f
		float ww = 400f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww)
		{// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh)
		{// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 按比例降低Bitmap分辨率 ，并降低质量，减少Bitmap的体积
	 */
	private Bitmap comp(Bitmap image)
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024)
		{// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.PNG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww)
		{// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh)
		{// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 创建下载和缓存文件夹
	 */
	public static void createDownloadFolder()
	{
		File file1 = new File(URLUtil.DOWNLOAD_CATALOG);
		File file2 = new File(URLUtil.CACHE_CATALOG);
		if (!file1.exists())
		{
			file1.mkdirs();
		}

		if (!file2.exists())
		{
			file2.mkdirs();
		}
	}

	/**
	 * 设备中是否安装了该APP
	 * 
	 * @param pm
	 *            包管理器
	 * @param pkgName
	 *            包名
	 * @return
	 */
	public static boolean isPkgInstalled(PackageManager pm, String pkgName)
	{
		PackageInfo packageInfo = null;
		try
		{
			packageInfo = pm.getPackageInfo(pkgName, 0);
		} catch (NameNotFoundException e)
		{
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null)
		{
			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 删除下载文件夹下的文件
	 * 
	 * @return
	 */
	public static void deleteDownloadFiles()
	{
		try
		{
			File file = new File(URLUtil.DOWNLOAD_CATALOG);
			if (null == file)
				return;
			for (File f : file.listFiles())
			{
				f.delete();
			}
		} catch (Exception e)
		{

		}

	}

	/**
	 * 将字节数转换成String
	 * 
	 * @param context
	 * @param sizeBytes
	 * @return
	 */
	public static String convertSizeToString(Context context, long sizeBytes)
	{
		String str = Formatter.formatShortFileSize(context, sizeBytes);
		if (str.contains("GB"))
		{
			return str.replace(" GB", "G");
		}
		if (str.contains("MB"))
		{
			return str.replace(" MB", "M");
		}
		if (str.contains("KB"))
		{
			return str.replace(" KB", "K");
		}
		return str;
	}

	/**
	 * 将字节数组转换成File
	 * 
	 * @param buf
	 * @param filePath
	 * @param fileName
	 * @return
	 * 
	 */
	public static File byte2File(byte[] buf, String filePath, String fileName)
	{
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try
		{
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory())
			{
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (bos != null)
			{
				try
				{
					bos.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (fos != null)
			{
				try
				{
					fos.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	// 通过Uri获得文件的真实地址
	public static String getRealFilePath(final Context context, final Uri uri)
	{
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme))
		{
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
		{
			Cursor cursor = context.getContentResolver().query(uri, new String[] { MediaColumns.DATA }, null, null,
					null);
			if (null != cursor)
			{
				if (cursor.moveToFirst())
				{
					int index = cursor.getColumnIndex(MediaColumns.DATA);
					if (index > -1)
					{
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

}
