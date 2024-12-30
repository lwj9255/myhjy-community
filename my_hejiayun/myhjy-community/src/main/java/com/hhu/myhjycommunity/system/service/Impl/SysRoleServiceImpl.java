package com.hhu.myhjycommunity.system.service.Impl;

import com.github.pagehelper.util.StringUtil;
import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.core.exception.CustomException;
import com.hhu.myhjycommunity.system.domain.SysRole;
import com.hhu.myhjycommunity.system.mapper.SysRoleMapper;
import com.hhu.myhjycommunity.system.mapper.SysUserRoleMapper;
import com.hhu.myhjycommunity.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role)
    {
        return sysRoleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询角色信息
     * @param userId
     * @return: 角色权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {

        //根据用户Id获取角色信息
        List<String> roleList = sysRoleMapper.selectRolePermissionByUserId(userId);

        //将角色信息List集合转换为Set集合
        Set<String> permsSet = new HashSet<>();
        for (String roleKey : roleList) {
            if(!StringUtils.isEmpty(roleKey)){
                //角色权限字符串可能会有多个,使用 逗号 连接
                permsSet.add(roleKey);
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return sysRoleMapper.selectRoleList(new SysRole());
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId)
    {
        return sysRoleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return sysRoleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role)
    {
        Long roleId = Objects.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = sysRoleMapper.checkRoleNameUnique(role.getRoleName());
        if (!Objects.isNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role)
    {
        Long roleId = Objects.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = sysRoleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (!Objects.isNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (!Objects.isNull(role.getRoleId()) && role.isAdmin())
        {
            throw new CustomException(500,"不允许操作超级管理员角色");
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return sysUserRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role)
    {
        return sysRoleMapper.updateRole(role);
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleById(Long roleId)
    {
        return sysRoleMapper.deleteRoleById(roleId);
    }


    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleByIds(Long[] roleIds)
    {
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new CustomException(500,role.getRoleName() + "已分配,不能删除" );
            }
        }
        return sysRoleMapper.deleteRoleByIds(roleIds);
    }


    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int insertRole(SysRole role) {
        return 0;
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRole(SysRole role) {
        return 0;
    }
}
