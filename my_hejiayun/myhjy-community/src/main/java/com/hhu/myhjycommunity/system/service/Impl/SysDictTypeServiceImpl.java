package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.system.domain.SysDictData;
import com.hhu.myhjycommunity.system.domain.SysDictType;
import com.hhu.myhjycommunity.system.mapper.SysDictDataMapper;
import com.hhu.myhjycommunity.system.mapper.SysDictTypeMapper;
import com.hhu.myhjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {

        return sysDictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return sysDictTypeMapper.selectDictTypeAll();
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return sysDictDataMapper.selectDictDataByType(dictType);
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return sysDictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return sysDictTypeMapper.selectDictTypeByType(dictType);
    }

    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        return sysDictTypeMapper.deleteDictTypeByIds(dictIds);
    }

    @Override
    public int insertDictType(SysDictType dictType) {
        return sysDictTypeMapper.insertDictType(dictType);
    }

    /**
     * 修改字典类型信息
     * @param dictType
     * @return: int
     */
    @Override
    public int updateDictType(SysDictType dictType) {
        return sysDictTypeMapper.updateDictType(dictType);
    }

}
