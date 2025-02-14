package com.hhu.myhjycommunity.web.controller.system;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.core.page.PageResult;
import com.hhu.myhjycommunity.common.utils.SecurityUtils;
import com.hhu.myhjycommunity.system.domain.SysDictType;
import com.hhu.myhjycommunity.system.service.SysDictDataService;
import com.hhu.myhjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询字典数据列表
     */
    @GetMapping("/list")
    public PageResult list(SysDictType dictType){
        startPage();
        List<SysDictType> list = sysDictTypeService.selectDictTypeList(dictType);
        return getData(list);
    }

    /**
     * 根据Id查询字典类型详细信息
     */
    @GetMapping(value = "/{dictId}")
    public BaseResponse getInfo(@PathVariable Long dictId){

        return BaseResponse.success(sysDictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictType dictType){
        if(UserConstants.NOT_UNIQUE.equals(sysDictTypeService.checkDictTypeUnique(dictType.getDictType()))){
            return BaseResponse.fail("新增字典"+dictType.getDictType()+"失败，字典类型已存在");
        }
        dictType.setCreateBy(SecurityUtils.getUserName());
        return toAjax(sysDictTypeService.insertDictType(dictType));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictType dictType){
        if(UserConstants.NOT_UNIQUE.equals(sysDictTypeService.checkDictTypeUnique(dictType.getDictType()))){
            return BaseResponse.fail("修改字典"+dictType.getDictType()+"失败，字典类型已存在");
        }
        dictType.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(sysDictTypeService.updateDictType(dictType));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public BaseResponse remove(@PathVariable  Long[] dictIds){

        return toAjax(sysDictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 清空缓存
     */
    @DeleteMapping("/clearCache")
    public BaseResponse clearCache(){
        sysDictTypeService.clearCache();
        return BaseResponse.success("清除缓存成功");
    }
}
