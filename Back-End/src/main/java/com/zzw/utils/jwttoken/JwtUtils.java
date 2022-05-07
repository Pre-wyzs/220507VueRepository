package com.zzw.utils.jwttoken;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtils {

    private static final long day = 1000*60*60*24;
    private static final String key = "zzw@lty";

    /**
     * token生成静态方法
     */
    public static String tokenGenerate(Integer id,String name,String power){
        //1、令牌生成器
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("id",id)
                .claim("name",name)
                .claim("power",power)  //
                //设置一下主题:就是为了区分识别用的
                .setSubject("Jwt-Login")
                //设置令牌有效时间
                .setExpiration(new Date(System.currentTimeMillis() + day))
                //设置令牌id
                .setId(UUID.randomUUID().toString())
                //Signature
                .signWith(SignatureAlgorithm.HS256,key)
                //最后拼接这三部分
                .compact();
        System.out.println(token);  //测试输出token
        return token;
    }

    /**
     * token解析
     */
    public static Claims tokenParse(String token){
        /**
         * token解析器
         */
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJwt = jwtParser.setSigningKey(key).parseClaimsJws(token);
        Claims claims = claimsJwt.getBody();
        return claims;
    }

}
