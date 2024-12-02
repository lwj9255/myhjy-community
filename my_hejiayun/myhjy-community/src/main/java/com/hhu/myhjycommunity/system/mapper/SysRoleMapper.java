package com.hhu.myhjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysRoleMapper extends BaseMapper {
    public List<String> selectRolePermissionByUserId(Long userId);
}
