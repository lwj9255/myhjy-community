package com.hhu.myhjycommunity.web.controller.community;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhu.myhjycommunity.common.constant.HttpStatus;
import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.core.page.PageResult;
import com.hhu.myhjycommunity.common.utils.ServletUtils;
import com.hhu.myhjycommunity.community.domain.HjyCommunity;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityDto;
import com.hhu.myhjycommunity.community.service.HjyCommunityService;
import com.hhu.myhjycommunity.community.service.impl.HjyCommunityServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/community")
public class HjyCommunityController extends BaseController {
    @Resource
    private HjyCommunityServiceImpl hjyCommunityService;

    /**
     * 多条件分页查询
     * @param hjyCommunity
     * @return
     */
    @GetMapping("/list")
    public PageResult list(HjyCommunity hjyCommunity){
        startPage();
        List<HjyCommunityDto> list=hjyCommunityService.queryList(hjyCommunity);
        return getData(list);
    }

    @PostMapping
    public BaseResponse add(@RequestBody HjyCommunity hjyCommunity){
        return toAjax(hjyCommunityService.insertCommunity(hjyCommunity));
    }
}
