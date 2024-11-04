package com.hhu.myhjycommunity.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hhu.myhjycommunity.common.constant.HttpStatus;
import com.hhu.myhjycommunity.common.core.domain.BaseResponse;
import com.hhu.myhjycommunity.common.core.page.PageDomain;
import com.hhu.myhjycommunity.common.core.page.PageResult;
import com.hhu.myhjycommunity.common.utils.ServletUtils;

import java.util.List;

// 基础控制器类
public class BaseController {
    // 当前记录索引页
    public static final String PAGE_NUM = "pageNum";
    // 每页显示记录数
    public static final String PAGE_SIZE = "pageSize";
    // 封装分页类
    public static PageDomain getPageDomain(){
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        return pageDomain;
    }
    // 设置分页参数
    protected void startPage(){
        PageDomain pageDomain = getPageDomain();

        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum,pageSize);
        }
    }
    // 相应分页数据
    protected PageResult getData(List<?> list){
        PageResult pageResult = new PageResult();
        pageResult.setCode(HttpStatus.SUCCESS);
        pageResult.setMsg("查询成功");
        pageResult.setRows(list);
        pageResult.setTotal(new PageInfo(list).getTotal());

        return pageResult;
    }

    /**
     * 响应返回结果
     * @param rows 受影响行数
     * @return
     */
    protected BaseResponse toAjax(int rows){
        return rows > 0 ? BaseResponse.success(rows) : BaseResponse.fail("操作失败");
    }
}
