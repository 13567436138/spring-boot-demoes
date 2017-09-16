package com.mark.demo.shiro.service;

import com.mark.demo.shiro.entity.ClientKey;


public interface ClientKeyService 
{
    /**
     * 通过key 查找
     * @param key String 用户key
     * @return ClientKey
     */
    ClientKey findByKey(String key);
    
    /**
     * 是否存在白名单中
     * @param key 用户key
     * @param accessType 访问类型
     * @param ip  Integer 客户端IP
     * @return Boolean true 是  false 否
     */
    Boolean isExistNetAddress(String key, String accessType, Integer ip);
}
