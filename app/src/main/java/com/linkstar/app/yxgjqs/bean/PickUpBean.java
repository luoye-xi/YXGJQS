package com.linkstar.app.yxgjqs.bean;

/**
 * Created by hx
 * Time 2018/7/31/031.
 * 待上门（送货）bean
 */

public class PickUpBean {

    public int id;
    public int statu;//1取件2送件
    public int type;//1待上门2待完成
    public String time;
    public String name_from;
    public String name_to;
    public String add_from;
    public String add_to;

    public PickUpBean(int id, int statu, int type, String time, String name_from, String name_to, String add_from, String add_to) {
        this.id = id;
        this.statu = statu;
        this.type = type;
        this.time = time;
        this.name_from = name_from;
        this.name_to = name_to;
        this.add_from = add_from;
        this.add_to = add_to;
    }
}
