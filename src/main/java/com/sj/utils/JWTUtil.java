package com.sj.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * jwt三部分：
 * header 头部
 * payload 负载
 * signature 签名
 */
public class JWTUtil {
    //过期时间
    private static final long EXPIRE_TIME = 5*60*1000;

    /**
     * 生成签名
     * @param username
     * @param password
     * @return 加密的token
     */
    public static String sign(String username,String password){
        try{
            //设置过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(password);
            //在负载中添加附带信息(也可以才header中添加)
            return JWT.create().withClaim("username",username).withExpiresAt(date).sign(algorithm);
        }catch (Exception e){
            return  null;
        }
    }

    /**
     * 获取payload中的附带信息
     * @param token
     * @return
     */
    public static String getMessage(String token){
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            //获取payload中的信息
            return decodedJWT.getClaim("username").toString();
        }catch (JWTDecodeException e){
            return null;
        }
    }

    /**
     * 验证token是否正确
     * @param username
     * @param password
     * @param token
     * @return
     */
    public static boolean verify(String username,String password,String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim("username",username).build();
            jwtVerifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
