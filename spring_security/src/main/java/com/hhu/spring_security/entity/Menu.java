package com.hhu.spring_security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "sys_menu")
public class Menu implements Serializable {

    @TableId
    private Long id;

    // 菜单名称
    private String menuName;

    // 路由地址
    private String path;

    // 组件路径
    private String component;

    // 菜单状态 (0 显示, 1 隐藏)
    private String visible;

    // 菜单状态 (0 正常, 1 停用)
    private String status;

    // 权限标识
    private String perms;

    // 菜单图标
    private String icon;

    // 创建者
    private String createBy;

    // 更新者
    private String updateBy;

    // 更新时间
    private Date updateTime;

    // 创建时间
    private Date createTime;

    // 备注
    private String remark;
}

