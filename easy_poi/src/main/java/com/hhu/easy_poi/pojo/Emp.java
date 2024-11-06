package com.hhu.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ExcelTarget("emps")
@Data
public class Emp implements Serializable {

    @Excel(name = "编号")
    private String id;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
    private Integer age;

    @Excel(name = "生日", exportFormat = "yyyy年MM月dd日")
    private Date bir;

    @Excel(name = "状态", replace = {"未激活_0", "激活_1"})
    private String status;

//    @Excel(name = "爱好",width=20.0,orderNum = "6")
//    private List<String> hobby;

    @Excel(name="头像图片",type = 2)
    private String photo;
}
