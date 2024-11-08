package com.hhu.easy_poi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;

@Data
@ExcelTarget("courses")
public class Course implements Serializable {
    @Excel(name="编号")
    private String cid;

    @Excel(name="订单编号")
    private String orderno;

    @Excel(name="课程名称")
    private String cname;

    @Excel(name="简介")
    private String brief;

    @Excel(name="价格")
    private double price;
}
