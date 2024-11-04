package com.hhu.myhjycommunity.community.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhu.myhjycommunity.common.core.domain.BaseEntity;
import org.springframework.data.redis.core.index.PathBasedRedisIndexDefinition;

/**
 * 小区对象
 */

public class HjyCommunity extends BaseEntity {
    //小区ID
    @TableId // 标识为主键
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long communityId;
    //小区名称
    private String communityName;
    //小区编码
    private String communityCode;
    //省区划码
    private String communityProvinceCode;
    //市区划码
    private String communityCityCode;
    //区县划码
    private String communityTownCode;
    //详细地址
    private String communityDetailedAddress;
    //经度
    private String communityLongitude;
    //维度
    private String communityLatitude;
    //物业ID
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long deptId;
    //小区排序
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private Long communitySort;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getCommunityProvinceCode() {
        return communityProvinceCode;
    }

    public void setCommunityProvinceCode(String communityProvenceCode) {
        this.communityProvinceCode = communityProvenceCode;
    }

    public String getCommunityCityCode() {
        return communityCityCode;
    }

    public void setCommunityCityCode(String communityCityCode) {
        this.communityCityCode = communityCityCode;
    }

    public String getCommunityTownCode() {
        return communityTownCode;
    }

    public void setCommunityTownCode(String communityTownCode) {
        this.communityTownCode = communityTownCode;
    }

    public String getCommunityDetailedAddress() {
        return communityDetailedAddress;
    }

    public void setCommunityDetailedAddress(String communityDetailedAddress) {
        this.communityDetailedAddress = communityDetailedAddress;
    }

    public String getCommunityLongitude() {
        return communityLongitude;
    }

    public void setCommunityLongitude(String communityLongitude) {
        this.communityLongitude = communityLongitude;
    }

    public String getCommunityLatitude() {
        return communityLatitude;
    }

    public void setCommunityLatitude(String communityLatitude) {
        this.communityLatitude = communityLatitude;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getCommunitySort() {
        return communitySort;
    }

    public void setCommunitySort(Long communitySort) {
        this.communitySort = communitySort;
    }
}
