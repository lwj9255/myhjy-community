package com.hhu.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ExcelTarget("users")
@Data
public class User implements Serializable {

    @Excel(name = "编号", orderNum = "1", replace = {"xxx_1", "nnn_2"})
    private String id;

    @Excel(name = "姓名", orderNum = "2")
    private String name;

    @Excel(name = "年龄", orderNum = "3", suffix = "岁")
    private Integer age;

    @Excel(name = "生日", orderNum = "4", width = 20.0, exportFormat = "yyyy年MM月dd日")
    private Date bir;

//    @ExcelIgnore
    @ExcelEntity(name = "身份信息")
    private Card card;

//    @ExcelIgnore
    @ExcelCollection(name = "订单", orderNum = "9")
    private List<Order> orders;

    @Excel(name = "状态", orderNum = "5", replace = {"未激活_0", "激活_1"})
    private String status;

    @Excel(name = "爱好",width=20.0,orderNum = "6")
    private List<String> hobby;

    @Excel(name="头像图片",type = 2,orderNum = "0",width = 12,height = 12)
    private String photo;
}
