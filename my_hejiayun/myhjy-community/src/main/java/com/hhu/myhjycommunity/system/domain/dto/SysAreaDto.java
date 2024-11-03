package com.hhu.myhjycommunity.system.domain.dto;

import java.io.Serializable;
import java.util.List;

public class SysAreaDto implements Serializable {
    private String code;
    private String name;
    private List<SysAreaDto> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SysAreaDto> getChildren() {
        return children;
    }

    public void setChildren(List<SysAreaDto> children) {
        this.children = children;
    }
}
