package com.hhu.myhjycommunity.system.service.Impl;

import com.hhu.myhjycommunity.common.constant.Constants;
import com.hhu.myhjycommunity.common.constant.UserConstants;
import com.hhu.myhjycommunity.common.core.exception.CustomException;
import com.hhu.myhjycommunity.common.utils.RedisCache;
import com.hhu.myhjycommunity.system.domain.SysDictData;
import com.hhu.myhjycommunity.system.domain.SysDictType;
import com.hhu.myhjycommunity.system.mapper.SysDictDataMapper;
import com.hhu.myhjycommunity.system.mapper.SysDictTypeMapper;
import com.hhu.myhjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private RedisCache redisCache;

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
        List<SysDictData> sysDictDataList = redisCache.getCacheObject(getCacheKey(dictType));
        if(!Objects.isNull(sysDictDataList)){
            return sysDictDataList;
        }

        sysDictDataList = sysDictDataMapper.selectDictDataByType(dictType);

        if(!Objects.isNull(sysDictDataList)){
            redisCache.setCacheObject(getCacheKey(dictType),sysDictDataList);
            return sysDictDataList;
        }

        return null;
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return sysDictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public SysDictType selectDictTypeByType(String dictType) {
        return sysDictTypeMapper.selectDictTypeByType(dictType);
    }

    /**
     * 批量删除
     * @param dictIds 需要删除的字典ID
     * @return
     */
    @Override
    public int deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType sysDictType = selectDictTypeById(dictId);
            if(sysDictDataMapper.countDictDataByType(sysDictType.getDictType()) > 0){
                throw new CustomException(500,"已分配，不能删除");
            }
        }

        int rows = sysDictTypeMapper.deleteDictTypeByIds(dictIds);

        if(rows > 0){
            clear();
        }

        return rows;
    }

    /**
     * 清除缓存
     */
    private void clear() {
        Collection<String> keys = redisCache.keys(Constants.SYS_DICT_KEY + "*");
        redisCache.deleteObject(keys);
    }

    @Override
    public int insertDictType(SysDictType dictType) {
        int row = sysDictTypeMapper.insertDictType(dictType);
        if(row > 0){
            clear();
        }
        return row;
    }

    /**
     * 修改字典类型信息
     * @param dictType
     * @return: int
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType) {
        //修改字典数据表
        SysDictType oldDictType = sysDictTypeMapper.selectDictTypeById(dictType.getDictId());
        sysDictDataMapper.updateDictDataType(oldDictType.getDictType(),dictType.getDictType());

        //修改字典类型表
        int row = sysDictTypeMapper.updateDictType(dictType);

        if(row > 0){
            clear();
        }
        return row;
    }

    @Override
    public String checkDictTypeUnique(String dictType) {
        SysDictType sysDictType = sysDictTypeMapper.checkDitcTypeUnique(dictType);

        if(!Objects.isNull(sysDictType)){
            return UserConstants.NOT_UNIQUE;
        }

        return UserConstants.UNIQUE;

    }

    @Override
    public void clearCache() {
        clear();
    }

    /**
     * 项目启动时初始化字典到缓存
     */
    @PostConstruct // 启动项目时就自动使用该方法
    public void init(){
        List<SysDictType> typelist = sysDictTypeMapper.selectDictTypeAll();
        for (SysDictType sysDictType : typelist) {
            sysDictDataMapper.selectDictDataByType(sysDictType.getDictType());
            redisCache.setCacheObject(getCacheKey(sysDictType.getDictType()),sysDictType);
        }
    }

    /**
     * 创建放入缓存的key
     * @param dictType
     * @return
     */
    private String getCacheKey(String dictType) {
        return Constants.SYS_DICT_KEY+dictType;
    }

}
