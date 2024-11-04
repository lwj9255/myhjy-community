package com.hhu.myhjycommunity.community.service.impl;

import com.hhu.myhjycommunity.community.domain.HjyCommunity;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityDto;
import com.hhu.myhjycommunity.community.mapper.HjyCommunityMapper;
import com.hhu.myhjycommunity.community.service.HjyCommunityService;
import com.hhu.myhjycommunity.system.service.SysAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class HjyCommunityServiceImpl implements HjyCommunityService {

    @Resource
    private HjyCommunityMapper hjyCommunityMapper;
    // 小区代码=前缀+时间戳，这里定义一下前缀
    private static final String CODE_PREFIX = "COMMUNITY_";

    @Override
    public List<HjyCommunityDto> queryList(HjyCommunity hjyCommunity) {
        return hjyCommunityMapper.queryList(hjyCommunity);
    }

    @Override
    public int insertCommunity(HjyCommunity hjyCommunity) {
        hjyCommunity.setCommunityCode(CODE_PREFIX+ System.currentTimeMillis());
        return hjyCommunityMapper.insert(hjyCommunity);
    }


}
