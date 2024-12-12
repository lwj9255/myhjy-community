package com.hhu.myhjycommunity.web.controller.system;

import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.page.PageResult;
import com.hhu.myhjycommunity.system.domain.SysDictData;
import com.hhu.myhjycommunity.system.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private SysDictDataService sysDictDataService;

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
}
