package com.linkstar.app.yxgjqs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hx
 * Time 2018/8/1/001.
 * 常量类
 */

public class ConstantUtil {
    /**
     * 订单状态待上门
     */
    public static final String ACTION_TYPE_PICKUP = "pickup";
    /**
     * 订单状态待完成
     */
    public static final String ACTION_TYPE_SEND = "send";
    /**
     * 订单状态已完成
     */
    public static final String ACTION_TYPE_DONE = "done";
    /**
     * 订单状态已取消
     */
    public static final String ACTION_TYPE_CANCLE = "cancel";

    /**
     * 订单类型取货
     */
    public static final int ACTION_STATU_GET = 1;
    /**
     * 订单类型送货
     */
    public static final int ACTION_STATU_SEND = 2;

    /**
     * 检查电话号码，如果为11位数字返回true，否则false
     */
    public static boolean checkPhoneNumber(String phoneNumber)
    {
        if (null == phoneNumber)
            return false;
        String regex = "[0-9]{11}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean b = matcher.matches();
        return b;
    }

    /**
     * 检查电验证码，如果为6位数字返回true，否则false
     */
    public static boolean checkCodeNumber(String code)
    {
        if (null == code)
            return false;
        String regex = "[0-9]{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        boolean b = matcher.matches();
        return b;
    }

    /**
     * 检查密码
     */
    public static boolean checkPassword(String password)
    {
        String regex = "[a-zA-Z0-9]{6,15}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        boolean b = matcher.matches();
        return b;
    }
}
