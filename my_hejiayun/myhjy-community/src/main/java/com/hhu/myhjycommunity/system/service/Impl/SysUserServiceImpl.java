package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.core.exception.CustomException;
import com.hhu.myhjycommunity.system.domain.SysRole;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.domain.SysUserPost;
import com.hhu.myhjycommunity.system.domain.SysUserRole;
import com.hhu.myhjycommunity.system.mapper.SysUserMapper;
import com.hhu.myhjycommunity.system.mapper.SysUserPostMapper;
import com.hhu.myhjycommunity.system.mapper.SysUserRoleMapper;
import com.hhu.myhjycommunity.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return sysUserMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return sysUserMapper.selectUserById(userId);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = sysUserMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 校验手机号是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserMapper.checkPhoneUnique(user.getPhonenumber());
        if (!Objects.isNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = Objects.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserMapper.checkEmailUnique(user.getEmail());
        if (!Objects.isNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (!Objects.isNull(user.getUserId()) && user.isAdmin())
        {
            throw new CustomException(500,"不允许操作超级管理员用户");
        }
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return sysUserMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {

        return sysUserMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return sysUserMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return sysUserMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return sysUserMapper.resetUserPwd(userName, password);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {

        return null;
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        return null;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(SysUser user) {
        //新增用户
        int rows = sysUserMapper.insertUser(user);
        //新增用户与岗位关联
        insertUserPost(user);
        //新增用户与角色关联
        insertUserRole(user);

        return rows;
    }

    /**
     * 新增用户与岗位关联
     * @param user
     */
    private void insertUserPost(SysUser user) {
        Long[] postIds = user.getPostIds();
        if(!Objects.isNull(postIds)){
            List<SysUserPost> list = new ArrayList<>();
            for (Long postId : postIds) {
                SysUserPost sysUserPost = new SysUserPost();
                sysUserPost.setUserId(user.getUserId());
                sysUserPost.setPostId(postId);

                list.add(sysUserPost);
            }
            if (list.size() > 0) {
                // 批量新增
                sysUserPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户与角色关联
     * @param user
     */
    private void insertUserRole(SysUser user) {
        Long[] roleIds = user.getRoleIds();
        if(!Objects.isNull(roleIds)){
            List<SysUserRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getUserId());
                sysUserRole.setRoleId(roleId);

                list.add(sysUserRole);
            }
            if (list.size() > 0) {
                // 批量新增
                sysUserRoleMapper.batchUserRole(list);
            }
        }
    }




    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();

        // 删除用户与角色关联
        sysUserRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(user);

        //删除用户与岗位关联
        sysUserPostMapper.deleteUserPostByUserId(userId);
        insertUserPost(user);

        return sysUserMapper.updateUser(user);
    }


    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        return 0;
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] userIds) {
        return 0;
    }
}
