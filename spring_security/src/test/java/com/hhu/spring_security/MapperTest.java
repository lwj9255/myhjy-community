package com.hhu.spring_security;

import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.mapper.UserMapper;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<SysUser> sysUsers = userMapper.selectList(null);
        System.out.println(sysUsers);
    }
}
