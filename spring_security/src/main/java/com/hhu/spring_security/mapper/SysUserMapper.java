package com.hhu.spring_security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhu.spring_security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
