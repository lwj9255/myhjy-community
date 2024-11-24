package com.hhu.myhjycommunity;

import com.hhu.myhjycommunity.system.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyhjyCommunityApplicationTests {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void tsetSysUserMapper() {
        System.out.println(sysUserMapper.selectUserByUserName("admin"));
    }
}
