package com.hhu.easy_poi.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ExcelTarget("loginUser")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUser implements Serializable {

    @Excel(name="用户ID",orderNum = "1")
    private String id;
    @Excel(name = "昵称",orderNum = "2")
    private String nickname;

    @Excel(name = "密码",orderNum = "3")
    private String password;

    @Excel(name="注册时间",orderNum="4",format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Excel(name ="状态" ,orderNum="5",replace = {"VIP_1","普通用户_0"})
    private String status;
}
