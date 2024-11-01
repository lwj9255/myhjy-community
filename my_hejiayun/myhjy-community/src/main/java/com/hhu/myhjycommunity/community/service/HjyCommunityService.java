package com.hhu.myhjycommunity.community.service;

import com.hhu.myhjycommunity.community.domain.HjyCommunity;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityDto;

import java.util.List;

public interface HjyCommunityService {
    List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity);
}
