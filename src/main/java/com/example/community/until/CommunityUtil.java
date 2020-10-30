package com.example.community.until;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {
    //生成随机字符串
    public static String generateUUID(){
        //replaceAll替换所有-的字符为空
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    //MD5加密
    //只能加密不能解密
    //密码后在加上随机字符串 提高安全性
    public static String md5(String key){
        //StringUtils.isBlank作用是大致判断空，详情百度
        if(StringUtils.isBlank(key)){
            return null;
        }
        //DigestUtils.md5DigestAsHex,加密
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
