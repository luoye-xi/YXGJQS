package com.linkstar.app.yxgjqs.net;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.linkstar.app.yxgjqs.base.BaseActivity;

import org.apache.http.entity.StringEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@SuppressWarnings("deprecation")
public class NetWork
{

	public static String result = "";

	/**
	 * get方式访问后台
	 * 
	 * @param url
	 * @param json
	 * @param request
	 */
	public static void httpJsonGet(String url, String json, RequestCallBack<String> request)
	{
		RequestParams params = new RequestParams();
		if (!BaseActivity.user_token.isEmpty())
		{
			Log.d("TAG", "token:" + BaseActivity.user_token);
			params.addHeader("token", BaseActivity.user_token);
		}
		if (null != json)
		{
			try
			{
				params.setBodyEntity(new StringEntity(json, "UTF-8"));
				params.setContentType("application/json");
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}

		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 30); // 设置超时时间 10s
		http.send(HttpRequest.HttpMethod.GET, url, params, request);

	}

	/**
	 * get查询数据库数据
	 * @param url
	 * @param map
	 * @param request
	 */
	public static void httpParamGet(String url, Map<String, Object> map, RequestCallBack<String> request)
	{
		RequestParams params = new RequestParams();
		if (!BaseActivity.user_token.isEmpty())
		{
			Log.d("TAG", "token:" + BaseActivity.user_token);
			params.addHeader("token", BaseActivity.user_token);
		}
		if (null != map)
		{
			if (map.size() != 0)
			{
				for (Map.Entry<String, Object> entry : map.entrySet())
				{
					params.addQueryStringParameter(entry.getKey(), (String) entry.getValue());
				}
			}
			params.setContentType("application/json");
		}

		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 30); // 设置超时时间 10s
		http.send(HttpRequest.HttpMethod.GET, url, params, request);

	}
	
	
	/**
	 * 带上传文件的 Post请求 异步的
	 * 
	 * @param url
	 * @param jsonStr
	 * @param request
	 */
	public static void httpPost(String url, String jsonStr, RequestCallBack<String> request)
	{
		RequestParams params = new RequestParams();
		if (!BaseActivity.user_token.isEmpty())
		{
			params.addHeader("token", BaseActivity.user_token);
		}
		// jsonStr = "{\"mobile\": \"13800138000\",\"password\": \"1\"}";
		// 只包含字符串参数时默认使用BodyParamsEntity，
		// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
		// params.addBodyParameter("name", "value");
		// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
		// params.addBodyParameter("picture", new File(picString));
		try
		{
			params.setBodyEntity(new StringEntity(jsonStr, "UTF-8"));
			params.setContentType("application/json");

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Log.d("TAG","jsonStr" +jsonStr.toString());
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params, request);
	}

    /**
     * 带上传文件的 Post请求 异步的
     * @param url
     * @param file
     * @param width
     * @param height
     * @param request
     */
	public static void httpFilePost(String url, File file, int width,int height, RequestCallBack<String> request)
	{
		RequestParams params = new RequestParams();
		if (!BaseActivity.user_token.isEmpty())
		{
			params.addHeader("token", BaseActivity.user_token);
		}
		// jsonStr = "{\"mobile\": \"13800138000\",\"password\": \"1\"}";
		// 只包含字符串参数时默认使用BodyParamsEntity，
		// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
		// params.addBodyParameter("name", "value");
		// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
		// params.addBodyParameter("picture", new File(picString));
		params.addBodyParameter("file",file);
		params.addBodyParameter("width",width+"");
		params.addBodyParameter("height",height+"");
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params, request);
	}

	/**
	 * post查询数据库数据
	 * @param url
	 * @param map
	 * @param request
	 */
	public static void httpPost(String url, Map<String, Object> map, RequestCallBack<String> request)
	{
		RequestParams params = new RequestParams();
		if (!BaseActivity.user_token.isEmpty())
		{
			Log.d("TAG", "token:" + BaseActivity.user_token);
			params.addHeader("token", BaseActivity.user_token);
		}
		if (null != map)
		{
			if (map.size() != 0)
			{
				for (Map.Entry<String, Object> entry : map.entrySet())
				{
					params.addQueryStringParameter(entry.getKey(), (String) entry.getValue());
				}
			}
			params.setContentType("application/json");
		}

		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 30); // 设置超时时间 10s
		http.send(HttpRequest.HttpMethod.POST, url, params, request);

	}

	// -------------------以上的代码 是 异步请求的， 以下的代码是同步请求的-------------------------//<br>
	/**
	 * Get同步请求 必须在异步块儿中执行
	 * 
	 * @param url
	 * @param userkey
	 * @param str
	 * @param sign
	 * @return
	 */
	public static String xutilsGetSync(String url, String userkey, String str, String sign)
	{
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("userkey", userkey);
		params.addQueryStringParameter("str", str);
		params.addQueryStringParameter("sign", sign);
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 10); // 设置超时时间
		try
		{
			ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.GET, url, params);
			// int statusCode = responseStream.getStatusCode();
			// Header[] headers = responseStream.getBaseResponse().getAllHeaders();
			return responseStream.readString();
		} catch (Exception e)
		{
			LogUtils.e(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Post同步请求 必须在异步块儿中执行
	 * @param url
	 * @param jsonStr
	 * @param request
	 * @return
	 */
	public String xutilsPostSync(String url, String jsonStr, RequestCallBack<String> request)
	{
		try
		{
			RequestParams params = new RequestParams();
			params.setBodyEntity(new StringEntity(jsonStr, "UTF-8"));
			HttpUtils http = new HttpUtils();
			http.configCurrentHttpCacheExpiry(3000 * 10); // 设置超时时间
			ResponseStream responseStream = http.sendSync(HttpRequest.HttpMethod.POST, url, params);
			return responseStream.readString();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
