package com.hhu.myhjycommunity.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogDemo {
    Logger logger = LoggerFactory.getLogger(LogDemo.class);

    @Test
    public void test1(){
        logger.info("hello logback");
    }

    @Test
    public void test2(){
        logger.trace("这个级别很少用，主要是用来追踪");
        logger.debug("测试的时候多打印日志来找bug");
        logger.info("系统日志，没什么问题，单纯想打印日志");
        logger.warn("错误很少见，不影响程序正常运行，酌情处理");
        logger.error("错误很严重，程序阻断了，需要立刻处理");
    }

    @Test
    public void test3(){
        log.trace("这个级别很少用，主要是用来追踪");
        log.debug("测试的时候多打印日志来找bug");
        log.info("系统日志，没什么问题，单纯想打印日志");
        log.warn("错误很少见，不影响程序正常运行，酌情处理");
        log.error("错误很严重，程序阻断了，需要立刻处理");
    }

    @Test
    public void test4() {

        // 使用 {} 作为占位符，而不是字符串拼接
        String name = "我是大佬";
        log.info("hello {}", name);
        log.debug("hello " + name);

        String userId = "10010";
        String orderId = "3242343253253535";
        log.debug("order is paying with userId:[{}] and orderId : [{}]", userId, orderId);

        // 不推荐使用 e.printStackTrace()，打印堆栈日志与业务日志混合
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            /*e.printStackTrace();*/
            log.error("/ by zero", e);
        }

        // 增加对低级别日志的判断方式
        // 方法1
        // 执行顺序：先拼接字符串 “hello” 和 name，然后执行 debug 方法，判断日志级别
        log.debug("hello" + name);

        // 方法2
        // 执行顺序：不提前拼接，先判断日志级别，然后选择是否执行 debug 方法，拼接字符串 “hello” 和 “world”
        // isDebugEnabled() 可以避免无用的字符串操作，提高性能
        if (log.isDebugEnabled()) {
            log.debug("hello" + name);
        }
    }

}
