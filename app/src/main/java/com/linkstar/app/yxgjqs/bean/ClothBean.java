package com.linkstar.app.yxgjqs.bean;

/**
 * Created by hx
 * Time 2018/8/1/001.
 * 订单详情衣服bean
 */

public class ClothBean {

    public int img;
    public String type;
    public String category;
    public int num;
    public String price;

    public ClothBean(int img, String type, String category, int num, String price) {
        this.img = img;
        this.type = type;
        this.category = category;
        this.num = num;
        this.price = price;
    }
}
