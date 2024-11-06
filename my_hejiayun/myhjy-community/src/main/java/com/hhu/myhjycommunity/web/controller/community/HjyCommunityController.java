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
    private HjyCommunityService hjyCommunityService;

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

    /**
     * 接收前端传来的json格式小区信息，封装好后加入小区表中
     * @param hjyCommunity
     * @return
     */
    @PostMapping
    public BaseResponse add(@RequestBody HjyCommunity hjyCommunity){
        return toAjax(hjyCommunityService.insertCommunity(hjyCommunity));
    }

    /**
     * 增加小区
     * @param communityId
     * @return
     */
    @GetMapping("/{communityId}")
    public BaseResponse getInfo(@PathVariable("communityId") Long communityId){
        return BaseResponse.success(hjyCommunityService.selectHjyCommunityById(communityId));
    }

    /**
     * 修改小区
     * @param hjyCommunity
     * @return
     */
    @PutMapping
    public BaseResponse edit(@RequestBody HjyCommunity hjyCommunity){
        return toAjax(hjyCommunityService.updateHjyCommunity(hjyCommunity));
    }

    @DeleteMapping("/{communityIds}")
    public BaseResponse remove(@PathVariable Long[] communityIds){
        return toAjax(hjyCommunityService.deleteHjyCommunityByIds(communityIds));
    }
}