package com.hhu.myhjycommunity.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hhu.myhjycommunity.system.domain.SysDept;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class TreeSelect implements Serializable {
    //节点ID
    private Long id;
    // 节点名称
    private String label;
    // 子节点
    @JsonInclude(JsonInclude.Include.NON_EMPTY) // 只在字段不为空时才会序列化
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    public TreeSelect(SysDept sysDept) {
        this.id = sysDept.getDeptId();
        this.label = sysDept.getDeptName();
        //TreeSelect::new 是构造方法的引用，表示将 TreeSelect 的构造函数应用于流中的每个元素
        //等于sysDept -> new TreeSelect(sysDept)
        this.children = sysDept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
