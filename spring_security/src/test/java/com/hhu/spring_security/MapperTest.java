package com.hhu.spring_security;

import com.hhu.spring_security.entity.SysUser;
import com.hhu.spring_security.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private SysUserMapper sysuserMapper;

    @Test
    public void testUserMapper(){
        List<SysUser> sysUsers = sysuserMapper.selectList(null);
        System.out.println(sysUsers);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testBcryp(){
        // 对原始密码进行加密
        String e1 = passwordEncoder.encode("123456");
        System.out.println(e1);// $2a$10$P/HT/R/JnE.NYVBJ5YEvoeRd.LaTpEWtHhn6erTP1O8c1MbiNX.56
        String e2 = passwordEncoder.encode("123456");
        System.out.println(e2);
        // 使用随机盐值进行加密，所以每次加密结果都不一样，哪怕原始密码是一样的
        System.out.println(e1.equals(e2));

        // 对比原始密码和加密后的密码
        System.out.println(passwordEncoder.matches("123456","$2a$10$P/HT/R/JnE.NYVBJ5YEvoeRd.LaTpEWtHhn6erTP1O8c1MbiNX.56"));
    }
}
