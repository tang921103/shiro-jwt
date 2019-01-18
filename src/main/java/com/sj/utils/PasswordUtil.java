package com.sj.utils;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 用MD5给密码加密
 */
public class PasswordUtil {
    public static String encodePwd(String username ,String password){
        SimpleHash simpleHash = new SimpleHash("MD5",password,username,1024);
        return simpleHash.toString();
    }
}
