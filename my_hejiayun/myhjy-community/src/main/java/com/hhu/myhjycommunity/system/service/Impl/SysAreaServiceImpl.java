package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.system.domain.SysArea;
import com.hhu.myhjycommunity.system.domain.dto.SysAreaDto;
import com.hhu.myhjycommunity.system.mapper.SysAreaMapper;
import com.hhu.myhjycommunity.system.service.SysAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysAreaServiceImpl implements SysAreaService {
    @Resource
    private SysAreaMapper sysAreaMapper;

    @Override
    public List<SysAreaDto> findAreaAsTree() {
        // 获取表中数据
        List<SysArea> list = sysAreaMapper.findAll();

        return list.stream() // 把集合转化成流
                .filter(area -> area.getParentCode().equals(0)) // 筛选没有父节点的对象作为该根节点
                .map(area -> { // 将流中的每个元素通过指定的函数进行转换
                    SysAreaDto sysAreaDto = new SysAreaDto();
                    sysAreaDto.setCode(area.getCode());
                    sysAreaDto.setName(area.getName());
                    sysAreaDto.setChildren(getChildrenArea(sysAreaDto, list)); // 这里的list就是最开始的，流操作不会改变原来的list
                    return sysAreaDto; // 这个return是对map里的函数进行返回
                }).collect(Collectors.toList()); // 将流中的元素收集起来，并将其转换为一个 List 集合
    }

    private List<SysAreaDto> getChildrenArea(SysAreaDto sysAreaDto, List<SysArea> list) {
        List<SysArea> subAreaList = list.stream()
                // 含有所有结点的list的结点含有：name,id,pid
                // 传入的Dto对象是前面获取到的父节点
                // 令所有节点的pid=父节点的id，即可获得当前父节点下的所有子节点
                .filter(area -> area.getParentCode().equals(sysAreaDto.getCode()))
                .collect(Collectors.toList());

        // 如果子节点列表不为空
        if (subAreaList != null && subAreaList.size() != 0) {
            return subAreaList.stream()
                    .map(area -> {
                        SysAreaDto subAreaDto = new SysAreaDto();
                        subAreaDto.setCode(area.getCode());
                        subAreaDto.setName(area.getName());
                        // 将该子节点的id=所有结点的pid，筛选出该子节点的子节点列表，递归
                        subAreaDto.setChildren(getChildrenArea(subAreaDto,list));
                        return subAreaDto;
                    }).collect(Collectors.toList());
        }

        return null;
    }
}
