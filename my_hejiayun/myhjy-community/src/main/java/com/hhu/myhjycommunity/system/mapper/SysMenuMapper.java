package com.hhu.myhjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper {
    public List<String> selectMenuPermsByUserId(Long userId);
}
