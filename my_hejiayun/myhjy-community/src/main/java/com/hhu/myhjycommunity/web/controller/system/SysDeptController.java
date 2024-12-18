package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.utils.SecurityUtils;
import com.hhu.myhjycommunity.system.domain.SysDept;
import com.hhu.myhjycommunity.system.service.SysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Resource
    private SysDeptService sysDeptService;


    /**
     * 获取部门列表
     * @param sysDept
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@mype.hasPerms('system:dept:list')")
    public BaseResponse findDeptList(SysDept sysDept){
        return BaseResponse.success(sysDeptService.selectDeptList(sysDept));
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping("/{deptId")
    public BaseResponse findDeptById(@PathVariable Long deptId){
        return BaseResponse.success(sysDeptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDept sysDept){
        if(UserConstants.NOT_UNIQUE.equals(sysDeptService.checkDeptNameUnique(sysDept))){
            return BaseResponse.fail("新增部门" + sysDept.getDeptName() + "失败，部门名已经存在");
        }
        sysDept.setCreateBy(SecurityUtils.getUserName());
        return toAjax(sysDeptService.insertDept(sysDept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDept sysDept){
        if(UserConstants.NOT_UNIQUE.equals(sysDeptService.checkDeptNameUnique(sysDept))){
            return BaseResponse.fail("修改部门" + sysDept.getDeptName() + "失败，部门名已经存在");
        }
        else if(sysDept.getDeptId().equals(sysDept.getParentId())){
            return BaseResponse.fail("修改部门" + sysDept.getDeptName() + "失败，上级部门不能是自己");
        }
        sysDept.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(sysDeptService.updateDept(sysDept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public BaseResponse delete(@PathVariable Long deptId){
        if(sysDeptService.hasChildByDeptId(deptId)){
            return BaseResponse.fail("存在下级部门,不允许删除");
        }
        if(sysDeptService.checkDeptExistUser(deptId)){
            return BaseResponse.fail("部门存在用户,不允许删除");
        }

        return toAjax(sysDeptService.deleteDeptById(deptId));
    }
}
