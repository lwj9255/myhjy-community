package com.hhu.myhjycommunity.service;

import com.hhu.myhjycommunity.entity.SysDept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 部门表(SysDept)表服务接口
 *
 * @author seeasheep
 * @since 2024-11-05 20:54:56
 */
public interface SysDeptService {

    /**
     * 通过ID查询单条数据
     *
     * @param deptId 主键
     * @return 实例对象
     */
    SysDept queryById(Long deptId);

    /**
     * 分页查询
     *
     * @param sysDept 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<SysDept> queryByPage(SysDept sysDept, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param sysDept 实例对象
     * @return 实例对象
     */
    SysDept insert(SysDept sysDept);

    /**
     * 修改数据
     *
     * @param sysDept 实例对象
     * @return 实例对象
     */
    SysDept update(SysDept sysDept);

    /**
     * 通过主键删除数据
     *
     * @param deptId 主键
     * @return 是否成功
     */
    boolean deleteById(Long deptId);

}
