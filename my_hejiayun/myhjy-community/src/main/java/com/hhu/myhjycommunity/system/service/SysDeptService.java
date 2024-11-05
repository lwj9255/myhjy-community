package com.hhu.myhjycommunity.system.service;

import com.hhu.myhjycommunity.system.domain.SysDept;

import java.util.List;

public interface SysDeptService {
    /**
     * 查询部门管理数据
     * @param sysDept
     * @return
     */
    public List<SysDept> selectDeptList(SysDept sysDept);
}
