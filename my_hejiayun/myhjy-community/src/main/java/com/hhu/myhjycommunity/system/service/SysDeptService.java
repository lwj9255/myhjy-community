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

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDept dept);

    /**
     * 是否存在部门子节点
     * @param deptId
     * @return: boolean
     */
    public boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);
}
