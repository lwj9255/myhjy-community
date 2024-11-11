package com.hhu.myhjycommunity.community.service.impl;

import com.hhu.myhjycommunity.common.utils.OrikaUtils;
import com.hhu.myhjycommunity.community.domain.HjyCommunity;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityDto;
import com.hhu.myhjycommunity.community.domain.vo.HjyCommunityVo;
import com.hhu.myhjycommunity.community.mapper.HjyCommunityMapper;
import com.hhu.myhjycommunity.community.service.HjyCommunityService;
import com.hhu.myhjycommunity.system.service.SysAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public HjyCommunity selectHjyCommunityById(Long communityId) {
        return hjyCommunityMapper.selectById(communityId);
    }

    @Override
    public int updateHjyCommunity(HjyCommunity hjyCommunity) {
        return hjyCommunityMapper.updateById(hjyCommunity);
    }

    @Override
    public int deleteHjyCommunityByIds(Long[] communityIds) {
        return hjyCommunityMapper.deleteBatchIds(Arrays.asList(communityIds));
    }

    @Override
    public List<HjyCommunityVo> queryPullDown(HjyCommunity hjyCommunity) {
        List<HjyCommunityDto> hjyCommunityDtos = hjyCommunityMapper.queryList(hjyCommunity);
        List<HjyCommunityVo> hjyCommunityVos = hjyCommunityDtos.stream().map(dto -> {
            HjyCommunityVo hjyCommunityVo = OrikaUtils.convert(dto, HjyCommunityVo.class);
            return hjyCommunityVo;
        }).collect(Collectors.toList());
        return hjyCommunityVos;
    }


}
