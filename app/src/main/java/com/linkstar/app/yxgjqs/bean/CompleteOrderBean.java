package com.linkstar.app.yxgjqs.bean;

/**
 * Created by hx
 * Time 2018/8/1/001.
 */

public class CompleteOrderBean {

    public int id;
    public int statu;//1取件2送件
    public int action;//1已完成2已取消
    public int isEvaluate;//1未评价-2已评价
    public String time;
    public String name_from;
    public String name_to;
    public String add_from;
    public String add_to;
    public String reson;

    public CompleteOrderBean(int id, int statu, int action, int isEvaluate, String time, String name_from, String name_to, String add_from, String add_to, String reson) {
        this.id = id;
        this.statu = statu;
        this.action = action;
        this.isEvaluate = isEvaluate;
        this.time = time;
        this.name_from = name_from;
        this.name_to = name_to;
        this.add_from = add_from;
        this.add_to = add_to;
        this.reson = reson;
    }
}
