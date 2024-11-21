package com.hhu.spring_security2.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hhu.spring_security2.entity.CheckCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {

    // final 修饰表示这个变量在初始化后不能被重新赋值，确保注入的 Producer 是唯一且不可更改的。
    private final Producer producer;

    // @Autowired 加在构造器上：
    // Spring 会自动扫描 @Autowired 注解标记的构造器，并通过反射将容器中的 Producer Bean 注入到该构造器中。
    // 原因：
    // 1、spring 推荐这种方式
    // 2、构造注入可以从启动之初检查循环依赖问题
    // 3、保证依赖不可变（final关键字）
    // 4、保证依赖不为空（省去了我们对其检查）
    // 5、保证返回客户端（调用）的代码的时候是完全初始化的状态
    @Autowired
    public KaptchaController(Producer producer){
        this.producer = producer;
    }

    @GetMapping("/code/image")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.创建验证码文本
        String text = producer.createText();
        // 2.将验证码文本放入session（前后端不分离的情况下一般是放入session中）
        CheckCode checkCode = new CheckCode(text);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,checkCode);
        //3.将验证码图片返回，禁止验证码图片缓存
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        //4.创建验证码图片，设置contentType
        BufferedImage bufferedImage = producer.createImage(text);
        response.setContentType("image/png");
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
    }
}
