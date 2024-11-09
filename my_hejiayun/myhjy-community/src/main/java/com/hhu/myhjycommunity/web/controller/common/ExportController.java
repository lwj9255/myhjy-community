package com.hhu.myhjycommunity.web.controller.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.hhu.myhjycommunity.common.controller.BaseController;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.utils.ExcelUtils;
import com.hhu.myhjycommunity.community.domain.HjyCommunity;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityDto;
import com.hhu.myhjycommunity.community.domain.dto.HjyCommunityExcelDto;
import com.hhu.myhjycommunity.community.service.HjyCommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exportExcel")
public class ExportController extends BaseController {

    @Resource
    private HjyCommunityService communityService;

    @GetMapping("/exportCommunityExcel")
    public BaseResponse exportExcel(HjyCommunity hjyCommunity, HttpServletResponse response){
        startPage();
        List<HjyCommunityDto> hjyCommunityDtos = communityService.queryList(hjyCommunity);

        List<HjyCommunityExcelDto> excelDtos = hjyCommunityDtos.stream().map(hjyCommunityDto -> {
            HjyCommunityExcelDto excelDto = new HjyCommunityExcelDto();
            excelDto.setCommunityId(hjyCommunityDto.getCommunityId());
            excelDto.setCommunityName(hjyCommunityDto.getCommunityName());
            excelDto.setCommunityCode(hjyCommunityDto.getCommunityCode());
            excelDto.setCommunityProvinceName(hjyCommunityDto.getCommunityProvinceName());
            excelDto.setCommunityCityName(hjyCommunityDto.getCommunityCityName());
            excelDto.setCommunityTownName(hjyCommunityDto.getCommunityTownName());
            excelDto.setCreateTime(hjyCommunityDto.getCreateTime());
            excelDto.setRemark(hjyCommunityDto.getRemark());
            return excelDto;
        }).collect(Collectors.toList());

        ExcelUtils.exportExcel(excelDtos,HjyCommunityExcelDto.class,"小区信息",response,new ExportParams("小区信息列表","小区信息"));
        return BaseResponse.success("导出成功");
    }
}
