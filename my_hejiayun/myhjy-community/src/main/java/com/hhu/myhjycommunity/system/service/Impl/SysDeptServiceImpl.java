package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.core.domain.TreeSelect;
import com.hhu.myhjycommunity.common.core.exception.CustomException;
import com.hhu.myhjycommunity.system.domain.SysDept;
import com.hhu.myhjycommunity.system.mapper.SysDeptMapper;
import com.hhu.myhjycommunity.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl implements SysDeptService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询所有部门数据
     *
     * @param sysDept
     * @return
     */
    @Override
    public List<SysDept> selectDeptList(SysDept sysDept) {
        return sysDeptMapper.selectDeptList(sysDept);
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return sysDeptMapper.selectDeptById(deptId);
    }

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return
     */
    @Override
    public int insertDept(SysDept dept) {
        //获取当前节点的父节点
        SysDept parent = sysDeptMapper.selectDeptById(dept.getParentId());

        //父节点不是正常状态就不允许增加子节点
        if (!UserConstants.DEPT_NORMAL.equals(parent.getStatus())) {
            throw new CustomException(500, "部门停用，不允许新增");
        }

        //设置ancesotors 祖籍列表字段，记录的是当前节点的所有父节点id
        dept.setAncestors(parent.getAncestors() + "," + dept.getParentId());

        return sysDeptMapper.insertDept(dept);
    }

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return
     */
    @Override
    public int updateDept(SysDept dept) {
        //获取父节点
        SysDept newParent = sysDeptMapper.selectDeptById(dept.getParentId());

        //获取旧节点数据
        SysDept oldDept = sysDeptMapper.selectDeptById(dept.getDeptId());

        if (!Objects.isNull(newParent) && !Objects.isNull(oldDept)) {
            //设置最新的祖籍列表
            String newAncestors = newParent.getAncestors() + "," + newParent.getDeptId();
            dept.setAncestors(newAncestors);

            //修改子元素关系
            String oldAncestors = oldDept.getAncestors();
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }

        return sysDeptMapper.updateDept(dept);

    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父id集合
     * @param oldAncestors 旧的父id集合
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = sysDeptMapper.selectChildrenDeptById(deptId);
        if (children.size() > 0) {
            for (SysDept child : children) {
                child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
            }
            sysDeptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门信息
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public int deleteDeptById(Long deptId) {
        return sysDeptMapper.deleteDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        SysDept sysDept = sysDeptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (!Objects.isNull(sysDept)) {
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;
    }

    /**
     * 是否存在子节点
     *
     * @param deptId
     * @return
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = sysDeptMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = sysDeptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 构建前端需要的下拉树结构
     *
     * @param sysDeptList
     * @return
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> sysDeptList) {
        List<SysDept> deptTrees = buildDeptTree(sysDeptList);
        List<TreeSelect> treeSelects = deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());

        return treeSelects;
    }

    /**
     * 构建部门树结构
     *
     * @param sysDeptList
     * @return
     */
    private List<SysDept> buildDeptTree(List<SysDept> sysDeptList) {
        // 要返回的list
        List<SysDept> returnList = new ArrayList<>();

        // 获取所有部门的deptId
        List<Long> deptIdList = new ArrayList<>();
        for (SysDept sysDept : sysDeptList) {
            deptIdList.add(sysDept.getDeptId());
        }

        sysDeptList.stream()
                .filter(dept -> !deptIdList.contains(dept.getParentId())) // 如果它的父辈编号不在deptId列表中，说明它是顶级节点
                .forEach(dept -> {
                    // 从顶级节点开始递归获取子节点
                    recursionFn(sysDeptList, dept);
                    returnList.add(dept);
                });

        return returnList;
    }


    /**
     * 递归操作
     *
     * @param sysDeptList
     * @param dept
     */
    private void recursionFn(List<SysDept> sysDeptList, SysDept dept) {
        //得到子节点
        List<SysDept> childList = getChildList(sysDeptList, dept);
        dept.setChildren(childList);
        for (SysDept child : childList) {
            // 判断子节点是否还有子节点
            if (hasChild(sysDeptList, child)) {
                //递归调用
                recursionFn(sysDeptList, child);
            }
        }
    }


    /**
     * 获取子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> sysDeptList, SysDept dept) {
        List<SysDept> subList = new ArrayList<>();

        subList = sysDeptList.stream()
                // 如果父节点id不是空且父节点id=传入节点的id，说明该节点是传入节点的子节点，筛选进返回子节点列表中
                .filter(sysDept -> !Objects.isNull(sysDept.getParentId())
                        && sysDept.getParentId().longValue() == dept.getDeptId().longValue())
                .collect(Collectors.toList());

        return subList;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> sysDeptList, SysDept sysDept) {
        return getChildList(sysDeptList, sysDept).size() > 0 ? true : false;
    }
}
