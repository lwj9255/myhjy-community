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

    /**
     * 根据Id查询小区信息
     * @param communityId
     * @return
     */
    HjyCommunity selectHjyCommunityById(Long communityId);

    /**
     * 修改小区
     * @param hjyCommunity
     * @return
     */
    int updateHjyCommunity(HjyCommunity hjyCommunity);

    /**
     * 根据ID删除小区
     * @param communityIds
     * @return
     */
    int deleteHjyCommunityByIds(Long[] communityIds);
}
