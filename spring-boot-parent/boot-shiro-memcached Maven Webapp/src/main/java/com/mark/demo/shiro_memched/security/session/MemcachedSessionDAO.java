package com.mark.demo.shiro_memched.security.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.mark.demo.shiro_memched.entity.User;
import com.mark.demo.shiro_memched.utils.MemcachedUtils;
import com.mark.demo.shiro_memched.utils.StringUtils;

/*
*hxp(hxpwangyi@126.com)
*2017年9月15日
*
*/
public class MemcachedSessionDAO extends AbstractSessionDAO implements CustomSessionDAO {

private static final Logger logger         = LoggerFactory.getLogger(MemcachedSessionDAO.class);
    
    public static final String  SESSION_GROUPS = "memcached_shiro_session_group";
    
    public static final String  SESSION_PREFIX = "session_";
    public static final String  SESSION_FIELD_PREFIX = "field_";
    
    @Override
    public void update(Session session) throws UnknownSessionException
    {
        if (session == null || session.getId() == null) { return; }
        String key = String.valueOf(SESSION_PREFIX + session.getId());
        int timeoutSeconds = (int) (session.getTimeout() / 1000);
        MemcachedUtils.set(key, session,timeoutSeconds);
        User principal = null;
        SimplePrincipalCollection collection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (null != collection)
        {
            principal = (User) collection.getPrimaryPrincipal();
        }
            
        String principalId = principal != null ? principal.getId()+"": StringUtils.EMPTY;
        //JedisUtils.setMapField(SESSION_GROUPS, key, principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
        MemcachedUtils.setGroupField(SESSION_GROUPS, SESSION_FIELD_PREFIX+key, principalId + "|" + session.getTimeout() + "|" + session.getLastAccessTime().getTime());
    }
    
    /**
     * 清空会话及缓存
     */
    public static void clean()
    {
    	/*Map<String, String> map=JedisUtils.getMap(SESSION_GROUPS);
    	JedisUtils.del(SESSION_GROUPS);
    	for (Map.Entry<String, String> entry : map.entrySet())
        {
            JedisUtils.del(entry.getKey());
            if (logger.isDebugEnabled())
            {
                logger.debug("remove session {} ", entry.getKey());
            }
        }*/
    	MemcachedUtils.delGroup(SESSION_GROUPS);
    
    }
    
    @Override
    public void delete(Session session)
    {
        if (session == null || session.getId() == null) { return; }
        String key = String.valueOf(SESSION_PREFIX + session.getId());
        //JedisUtils.removeMapField(SESSION_GROUPS,key);
        //JedisUtils.del(key);
        MemcachedUtils.delGroupField(SESSION_GROUPS, SESSION_FIELD_PREFIX+key);
        MemcachedUtils.delete(key);
        
    }
    
    @Override
    public Collection<Session> getActiveSessions()
    {
        return getActiveSessions(true);
    }
    
    /**
     * 获取活动会话
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave)
    {
        return getActiveSessions(includeLeave, null, null);
    }
    
    /**
     * 获取活动会话
     * @param includeLeave 是否包括离线（最后访问时间大于3分钟为离线会话）
     * @param principal 根据登录者对象获取活动会话
     * @param filterSession 不为空，则过滤掉（不包含）这个会话。
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions(boolean includeLeave, Object principal, Session filterSession)
    {
    	Set<Session> sessions = Sets.newHashSet();
    	Set<String> fields =(Set<String>)MemcachedUtils.get(SESSION_GROUPS);
    	if(fields==null){
        	return sessions;
        }
    	Iterator<String> it=fields.iterator();
    	while (it.hasNext())
        {
    		String field=it.next();
    		String value=(String)MemcachedUtils.get(field);
            if (StringUtils.isNotBlank(field)&&StringUtils.isNotBlank(value) )
            {
                String[] ss = StringUtils.split(value, "|");
                if (ss != null && ss.length == 3)
                {
                    SimpleSession session = new SimpleSession();
                    session.setId(field);
                    session.setAttribute("principalId", ss[0]);
                    session.setTimeout(Long.valueOf(ss[1]));
                    session.setLastAccessTime(new Date(Long.valueOf(ss[2])));
                    try
                    {
                        // 验证SESSION
                        session.validate();
                        boolean isActiveSession = false;
                        // 不包括离线并符合最后访问时间小于等于3分钟条件。
                        if (includeLeave )
                        {
                            isActiveSession = true;
                        }
                        // 过滤掉的SESSION
                        if (filterSession != null && filterSession.getId().equals(session.getId()))
                        {
                            isActiveSession = false;
                        }
                        if (isActiveSession)
                        {
                            sessions.add(session);
                        }
                    }
                    // SESSION验证失败
                    catch (Exception e2)
                    {
                    	MemcachedUtils.delGroupField(SESSION_GROUPS, field);
                    }
                }
                // 存储的SESSION不符合规则
                else
                {
                	MemcachedUtils.delGroupField(SESSION_GROUPS, field);
                }
            }
            // 存储的SESSION无Value
            else if (StringUtils.isNotBlank(field))
            {
            	MemcachedUtils.delGroupField(SESSION_GROUPS, field);
            }
        }
        logger.info("getActiveSessions size: {} ", sessions.size());
    	
    	return sessions;
    	
        /*Set<Session> sessions = Sets.newHashSet();
        try
        {
            Map<String, String> map =JedisUtils.getMap(SESSION_GROUPS);
            if(map==null){
            	return sessions;
            }
            for (Map.Entry<String, String> e : map.entrySet())
            {
                if (StringUtils.isNotBlank(e.getKey()) && StringUtils.isNotBlank(e.getValue()))
                {
                    String[] ss = StringUtils.split(e.getValue(), "|");
                    if (ss != null && ss.length == 3)
                    {
                        SimpleSession session = new SimpleSession();
                        session.setId(e.getKey());
                        session.setAttribute("principalId", ss[0]);
                        session.setTimeout(Long.valueOf(ss[1]));
                        session.setLastAccessTime(new Date(Long.valueOf(ss[2])));
                        try
                        {
                            // 验证SESSION
                            session.validate();
                            boolean isActiveSession = false;
                            // 不包括离线并符合最后访问时间小于等于3分钟条件。
                            if (includeLeave )
                            {
                                isActiveSession = true;
                            }
                            // 过滤掉的SESSION
                            if (filterSession != null && filterSession.getId().equals(session.getId()))
                            {
                                isActiveSession = false;
                            }
                            if (isActiveSession)
                            {
                                sessions.add(session);
                            }
                        }
                        // SESSION验证失败
                        catch (Exception e2)
                        {
                        	JedisUtils.removeMapField(SESSION_GROUPS, e.getKey());
                        	JedisUtils.del(e.getKey());
                        }
                    }
                    // 存储的SESSION不符合规则
                    else
                    {
                    	JedisUtils.removeMapField(SESSION_GROUPS, e.getKey());
                    	JedisUtils.del(e.getKey());
                    }
                }
                // 存储的SESSION无Value
                else if (StringUtils.isNotBlank(e.getKey()))
                {
                	JedisUtils.removeMapField(SESSION_GROUPS, e.getKey());
                	JedisUtils.del(e.getKey());
                }
            }
            logger.info("getActiveSessions size: {} ", sessions.size());
        }
        catch (Exception e)
        {
            logger.error("getActiveSessions", e);
        }
        
        return sessions;*/
    }
    
    @Override
    protected Serializable doCreate(Session session)
    {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.update(session);
       // SecurityUtils.getSubject()
        return sessionId;
    }
    
    @Override
    protected Session doReadSession(Serializable sessionId)
    {
        Session session = null;
        try
        {
            String key = String.valueOf(SESSION_PREFIX + sessionId);
            //session = (Session) JedisUtils.getObject(key);
            session = (Session)MemcachedUtils.get(key);
        }
        catch (Exception e)
        {
            logger.error("doReadSession {} {}", sessionId, e);
        }
        
        return session;
    }
    
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException
    {
        try
        {
            return super.readSession(sessionId);
        }
        catch (UnknownSessionException e)
        {
            return null;
        }
    }

}
