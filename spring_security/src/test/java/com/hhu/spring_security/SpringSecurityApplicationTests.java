package com.hhu.spring_security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 创建token
     */
    @Test
    public void testCreateJWT(){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("9527") // 设置唯一ID
                .setSubject("spring_security") // 设置主体
                .setIssuedAt(new Date()) // 设置签约时间
                .signWith(SignatureAlgorithm.HS256, "firstsecretkey");
                                                // 设置签名：使用HS256算法，并设置secretkey,这个key不能太短，不然会报错
        String jws = jwtBuilder.compact(); // 压缩成String形式,签名的JWT称为JWS
        System.out.println(jws);
    }

    /**
     * 解析token
     */
    @Test
    public void parserJwt(){
        String jws="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5NTI3Iiwic3ViIjoic3ByaW5nX3NlY3VyaXR5IiwiaWF0IjoxNzMxNDE4NzA1fQ.lWgiiRCY2Iv0n7jMbxFVJ9sP6OFg1U8LOqNgC6e-gwc";
        Claims claims = Jwts.parser()
                .setSigningKey("firstsecretkey")
                .parseClaimsJws(jws)
                .getBody();

        System.out.println(claims);

    }

    /**
     * 创建token,并设置过期时间
     */
    @Test
    public void testCreateJWT2(){
        long currentTimeMillis = System.currentTimeMillis();
        Date expTime = new Date(currentTimeMillis);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("9527") // 设置唯一ID
                .setSubject("spring_security") // 设置主体
                .setIssuedAt(new Date()) // 设置签约时间
                .setExpiration(expTime) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, "firstsecretkey");
        // 设置签名：使用HS256算法，并设置secretkey,这个key不能太短，不然会报错
        String jws = jwtBuilder.compact(); // 压缩成String形式,签名的JWT称为JWS
        System.out.println(jws);
    }

    /**
     * 自定义claims
     */
    @Test
    public void testCreateJWT3(){
//        long currentTimeMillis = System.currentTimeMillis();
//        Date expTime = new Date(currentTimeMillis);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("9527") // 设置唯一ID
                .setSubject("spring_security") // 设置主体
                .setIssuedAt(new Date()) // 设置签约时间
                .claim("role","xiaoming") // 设置自定义内容
                .signWith(SignatureAlgorithm.HS256, "firstsecretkey");
        // 设置签名：使用HS256算法，并设置secretkey,这个key不能太短，不然会报错
        String jws = jwtBuilder.compact(); // 压缩成String形式,签名的JWT称为JWS
        System.out.println(jws);
    }
}
