package com.hhu.spring_security.utils.controller;

import com.hhu.spring_security.common.Constants;
import com.hhu.spring_security.common.ResponseResult;
import com.hhu.spring_security.utils.RedisCache;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CaptchaController {
    @Autowired
    private RedisCache redisCache;

    /**
     * 生成验证码
     * @param response
     * @return
     */
    @GetMapping("/captchaImage")
    public ResponseResult getCode(HttpServletResponse response){
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //生成验证码，以及验证码唯一标识
        String uuid = UUID.randomUUID().toString().replaceAll("_", "");// replaceAll把生成的UUID中的下划线都去掉
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String code = specCaptcha.text().toLowerCase();// toLowerCase大写都换成小写

        // 保存到Redis
        redisCache.setCacheObject(key,code,1000, TimeUnit.SECONDS);

        //创建返回的MAP
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        map.put("img",specCaptcha.toBase64());

        return new ResponseResult(200,"验证码获取成功",map);
    }
}
