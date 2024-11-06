package com.hhu.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@ExcelTarget("card")
@AllArgsConstructor
@Data
public class Card implements Serializable {
    @Excel(name="身份证号",orderNum = "7")
    private String id;
    @Excel(name="家庭住址",orderNum = "8")
    private String address;
}
