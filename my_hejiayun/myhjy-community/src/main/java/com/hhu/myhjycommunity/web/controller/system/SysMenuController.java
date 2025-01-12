package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.utils.ServletUtils;
import com.hhu.myhjycommunity.system.domain.LoginUser;
import com.hhu.myhjycommunity.system.domain.SysMenu;
import com.hhu.myhjycommunity.system.service.SysMenuService;
import com.hhu.myhjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单信息的管理
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/list")
    public BaseResponse list(SysMenu menu){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getSysUser().getUserId();
        List<SysMenu> sysMenuList = sysMenuService.selectMenuList(menu, userId);
        return BaseResponse.success(sysMenuList);
    }
}
