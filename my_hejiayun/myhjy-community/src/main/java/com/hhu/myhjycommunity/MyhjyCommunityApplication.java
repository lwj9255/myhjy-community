package com.hhu.myhjycommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.hhu.myhjycommunity.**.mapper")
@SpringBootApplication
public class MyhjyCommunityApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyhjyCommunityApplication.class, args);
        System.out.println("hejiayun启动成功");
    }

}
