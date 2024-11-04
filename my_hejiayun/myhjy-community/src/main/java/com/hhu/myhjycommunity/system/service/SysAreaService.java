package com.hhu.myhjycommunity.system.service;

import com.hhu.myhjycommunity.system.domain.dto.SysAreaDto;

import java.util.List;

public interface SysAreaService {
    List<SysAreaDto> findAreaAsTree();
}
