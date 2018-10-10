package com.linkstar.app.yxgjqs.bean;

/**
 * Created by hx
 * Time 2018/7/31/031.
 * 消息bean
 */

public class MessageBean {

    public String time;
    public String title;
    public String msg;

    public MessageBean(String time, String title, String msg) {
        this.time = time;
        this.title = title;
        this.msg = msg;
    }
}
