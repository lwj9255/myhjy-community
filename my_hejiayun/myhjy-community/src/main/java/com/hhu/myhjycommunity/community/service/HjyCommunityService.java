package com.hhu.myhjycommunity.community.service;

import com.hhu.myhjycommunity.community.domain.HjyCommunity;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityDto;

import java.util.List;

public interface HjyCommunityService {
    /**
     * 根据条件分页查询
     * @param hjyCommunity
     * @return
     */
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);

    /**
     * 添加小区
     * @param hjyCommunity
     * @return
     */
    int insertCommunity(HjyCommunity hjyCommunity);
}
