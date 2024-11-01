package com.hhu.myhjycommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.hhu.myhjycommunity.community.mapper")
@SpringBootApplication
public class MyhjyCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyhjyCommunityApplication.class, args);
    }

}
