package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.system.domain.SysDept;
import com.hhu.myhjycommunity.system.service.SysDeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Resource
    private SysDeptService sysDeptService;

    @GetMapping("/list")
    public BaseResponse findDeptList(SysDept sysDept){
        return BaseResponse.success(sysDeptService.selectDeptList(sysDept));
    }
}
