package com.hhu.myhjycommunity.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhu.myhjycommunity.system.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper {
    /**
     * 根据用户ID查询权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 查询全部菜单
     */
    public List<SysMenu> selectMenuTreeAll();

    public List<SysMenu> selectMenuTreeByUserId(Long userId);
}
