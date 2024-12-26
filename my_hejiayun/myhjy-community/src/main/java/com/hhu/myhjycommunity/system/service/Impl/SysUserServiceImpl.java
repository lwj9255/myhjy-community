package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.core.exception.CustomException;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.mapper.SysUserMapper;
import com.hhu.myhjycommunity.system.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

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

        return 0;
    }


    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(SysUser user) {
        return 0;
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
