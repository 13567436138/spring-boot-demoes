package com.mark.demo.shiro.mapper;

import org.apache.ibatis.annotations.Param;

import com.mark.demo.shiro.anno.MyBatisDao;
import com.mark.demo.shiro.base.GenericMapper;
import com.mark.demo.shiro.entity.ClientKey;


@MyBatisDao
public interface ClientKeyMapper extends GenericMapper<ClientKey>
{
    /**
     * 通过key 查找
     * @param accessType
     * @param key String 用户key
     * @return ClientKey
     */
    ClientKey findByKey(@Param("accessType") String accessType, @Param("key") String key);
    
    /**
     * 是否存在白名单中
     * @param key 用户key
     * @param accessType 访问类型
     * @param ip  Integer 客户端IP
     * @return Boolean true 是  false 否
     */
    ClientKey isExistNetAddress(@Param("key") String key, @Param("accessType") String accessType, @Param("ip") Integer ip);
}
