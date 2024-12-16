package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.core.page.PageResult;
import com.hhu.myhjycommunity.common.utils.SecurityUtils;
import com.hhu.myhjycommunity.system.domain.SysDictData;
import com.hhu.myhjycommunity.system.service.SysDictDataService;
import com.hhu.myhjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询字典数据列表
     * @param sysDictData
     * @return
     */
    @GetMapping("/list")
    public PageResult list(SysDictData sysDictData){
        startPage();
        List<SysDictData> list = sysDictDataService.selectDictDataList(sysDictData);
        return getData(list);
    }

    /**
     * 根据id查询字典详细信息
     */
    @GetMapping(value = "/{dictCode}")
    public BaseResponse getInfo(@PathVariable Long dictCode){

        return BaseResponse.success(sysDictDataService.selectDictDataById(dictCode));
    }


    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public BaseResponse getDictByType(@PathVariable String dictType){
        return BaseResponse.success(sysDictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictData dictData){
        dictData.setCreateBy(SecurityUtils.getUserName());
        return toAjax(sysDictDataService.insertDictData(dictData));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictData dictData){
        dictData.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(sysDictDataService.updateDictData(dictData));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictCodes}")
    public BaseResponse remove(@PathVariable Long[] dictCodes)
    {
        return toAjax(sysDictDataService.deleteDictDataByIds(dictCodes));
    }
}
