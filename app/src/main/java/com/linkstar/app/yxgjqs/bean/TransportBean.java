package com.linkstar.app.yxgjqs.bean;

/**
 * Created by hx
 * Time 2018/8/3/003.
 * 运输信息bean
 */

public class TransportBean {

    public int statu;//1取件检查2商家检查3送件检查
    public int img;
    public String name;
    public String time;

    public TransportBean(int statu, int img, String name, String time) {
        this.statu = statu;
        this.img = img;
        this.name = name;
        this.time = time;
    }
}
