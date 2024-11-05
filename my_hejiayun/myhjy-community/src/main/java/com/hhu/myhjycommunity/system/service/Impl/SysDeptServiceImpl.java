package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.system.domain.SysDept;
import com.hhu.myhjycommunity.system.mapper.SysDeptMapper;
import com.hhu.myhjycommunity.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> selectDeptList(SysDept sysDept) {
        return sysDeptMapper.selectDeptList(sysDept);
    }
}
