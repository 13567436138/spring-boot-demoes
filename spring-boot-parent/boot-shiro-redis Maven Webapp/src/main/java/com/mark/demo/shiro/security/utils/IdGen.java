package com.mark.demo.shiro.security.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import com.mark.demo.shiro.utils.EncryptUtils;

public class IdGen implements SessionIdGenerator
{
    private static SecureRandom random = new SecureRandom();
    
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 使用SecureRandom随机生成Long. 
     */
    public static long randomLong()
    {
        return Math.abs(random.nextLong());
    }
    
    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length)
    {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncryptUtils.desEncrypt(randomBytes.toString());
    }
    
    @Override
    public Serializable generateId(Session session)
    {
        return IdGen.uuid();
    }
}