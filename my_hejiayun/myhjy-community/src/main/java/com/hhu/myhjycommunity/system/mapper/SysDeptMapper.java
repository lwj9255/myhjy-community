package com.hhu.myhjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhu.myhjycommunity.system.domain.SysDept;

import java.util.List;

public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 查询部门管理数据
     * @param sysDept
     * @return
     */
    public List<SysDept> selectDeptList(SysDept sysDept);
}
