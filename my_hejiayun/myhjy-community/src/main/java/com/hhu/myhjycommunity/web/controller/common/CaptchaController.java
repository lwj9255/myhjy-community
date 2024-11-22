package com.hhu.myhjycommunity.web.controller.common;

import com.hhu.myhjycommunity.common.constant.Constants;
import com.hhu.myhjycommunity.common.utils.ChainedMap;
import com.hhu.myhjycommunity.common.utils.UUIDUtils;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@RestController
public class CaptchaController {
    // 把redis当做数据库或者消息队列来操作时，一般使用RedisTemplate来操作
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 生成验证码
     * @param response
     * @return
     */
    @GetMapping("/captchaImage")
    public ChainedMap getCode(HttpServletResponse response){
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //生成验证码和验证码唯一标识UUID
        String uuid = UUIDUtils.simpleUUID();
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String code = specCaptcha.text().toLowerCase();

        //保存到Redis
        redisTemplate.opsForValue().set(key,code, Duration.ofMinutes(10));

        return ChainedMap.create().set("uuid",uuid).set("img",specCaptcha.toBase64());
    }
}
