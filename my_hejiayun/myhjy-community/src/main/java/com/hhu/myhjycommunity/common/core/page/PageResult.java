package com.hhu.myhjycommunity.common.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询统一响应封装类
 */
public class PageResult implements Serializable {
    // 总记录数
    private long total;
    // 列表数据
    private List<?> rows;
    // 响应状态码
    private int code;
    // 响应信息
    private String msg;

    public PageResult() {
    }

    public PageResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
