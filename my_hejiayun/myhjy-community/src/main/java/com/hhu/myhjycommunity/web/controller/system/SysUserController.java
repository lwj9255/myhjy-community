package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.core.page.PageResult;
import com.hhu.myhjycommunity.common.utils.ChainedMap;
import com.hhu.myhjycommunity.common.utils.SecurityUtils;
import com.hhu.myhjycommunity.system.domain.SysRole;
import com.hhu.myhjycommunity.system.domain.SysUser;
import com.hhu.myhjycommunity.system.service.SysPostService;
import com.hhu.myhjycommunity.system.service.SysRoleService;
import com.hhu.myhjycommunity.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPostService sysPostService;

    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public PageResult list(SysUser user){
        startPage();
        List<SysUser> sysUserList = sysUserService.selectUserList(user);
        return getData(sysUserList);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public BaseResponse remove(@PathVariable Long[] userIds)
    {
        return toAjax(sysUserService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public BaseResponse resetPwd(@RequestBody SysUser user)
    {
        sysUserService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(sysUserService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public BaseResponse changeStatus(@RequestBody SysUser user)
    {
        sysUserService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(sysUserService.updateUserStatus(user));
    }


    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = {"/","/{userId}"})
    public ChainedMap getInfo(@PathVariable(value = "userId",required = false)Long userId){
        ChainedMap map = ChainedMap.create().set("code", 200).set("msg", "操作成功");

        List<SysRole> sysRoleList = sysRoleService.selectRoleAll();

        //用户关联角色信息
        if(SysUser.isAdmin(userId)){
            map.put("roles",sysRoleList);
        }else{
            List<SysRole> roleList = sysRoleList.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList());
            map.put("roles",roleList);
        }

        //用户关联部门信息
        map.put("posts",sysPostService.selectPostAll());

        //如果userid不为空，修改操作
        if(!Objects.isNull(userId)){
            map.put("data",sysUserService.selectUserById(userId));
            map.put("postIds",sysPostService.selectPostById(userId));
            map.put("roleIds",sysRoleService.selectRoleById(userId));
        }

        return map;
    }

    /**
     * 新增用户
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysUser sysUser){
        if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkUserNameUnique(sysUser.getUserName()))){
            return BaseResponse.fail("新增用户'"+sysUser.getUserName()+"'失败，登录账号已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))){
            return BaseResponse.fail("新增用户'"+sysUser.getUserName()+"'失败，手机号码已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))){
            return BaseResponse.fail("新增用户'"+sysUser.getUserName()+"'失败，邮箱账号已存在");
        }

        sysUser.setCreateBy(SecurityUtils.getUserName());
        sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));

        return toAjax(sysUserService.insertUser(sysUser));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysUser sysUser){

        sysUserService.checkUserAllowed(sysUser);

        if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))){
            return BaseResponse.fail("修改用户'"+sysUser.getUserName()+"'失败，手机号码已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))){
            return BaseResponse.fail("修改用户'"+sysUser.getUserName()+"'失败，邮箱账号已存在");
        }

        sysUser.setUpdateBy(SecurityUtils.getUserName());

        return toAjax(sysUserService.updateUser(sysUser));
    }



}
