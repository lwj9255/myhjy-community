package com.hhu.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ExcelTarget("loginUrl")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUrl implements Serializable {
    @Excel(name="用户ID",orderNum = "1")
    private String id;
    @Excel(name = "请求类型",orderNum = "2")
    private String type;

    @Excel(name = "访问地址",orderNum = "3")
    private String url;
}
