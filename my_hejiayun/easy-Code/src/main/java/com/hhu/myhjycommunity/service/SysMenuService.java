package com.hhu.myhjycommunity.service;

import com.hhu.myhjycommunity.entity.SysMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author seeasheep
 * @since 2024-12-08 18:59:39
 */
public interface SysMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenu queryById(Long menuId);

    /**
     * 分页查询
     *
     * @param sysMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<SysMenu> queryByPage(SysMenu sysMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    boolean deleteById(Long menuId);

}
