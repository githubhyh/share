package com.dm.shiro;

import com.dm.utils.RedisSessionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author hu.yuhao
 * @date 2020-4-16
 * 通过redis做持久化，共享session
 *  session 序列化问题
 * */
@Component
public class RedisSessionDao extends AbstractSessionDAO {
    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private final String redisPrefix = "SHIRO_REDIS_SESSION_KEY:";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        try {
            RedisSessionUtils.set(redisPrefix+sessionId.toString(), session, 30, TimeUnit.MINUTES);
        }catch (Exception e) {
            logger.error("create session failed, sessionId--[{}], msg--[{}]", sessionId.toString(), e.getMessage());
            e.printStackTrace();
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        String sessionId = serializable.toString();
        Session session = null;
        try {
             session = (Session)RedisSessionUtils.get(redisPrefix+sessionId);
        }catch (Exception e) {
            logger.error("read session failed, sessionId--[{}], msg--[{}]", sessionId.toString(), e.getMessage());
            e.printStackTrace();
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        String sessionId = session.getId().toString();
        try {
            RedisSessionUtils.set(redisPrefix+sessionId, session, 30, TimeUnit.MINUTES);
        }catch (Exception e) {
            logger.error("session update fail, sessionId--[{}], msg--[{}]", sessionId, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Session session) {
        String sessionId = session.getId().toString();
        try {
            RedisSessionUtils.del(redisPrefix + sessionId);
        }catch (Exception e) {
            logger.error("delete session failed, sessionId--[{}], msg--[{}]", sessionId, e.getMessage());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<String> keys = RedisSessionUtils.keys(redisPrefix + "*");
        if (keys != null && !keys.isEmpty()) {
            List<Object> objects = RedisSessionUtils.multiGet(keys);
            objects.stream().forEach((obj)->{sessions.add((Session)obj);});
        }
        return sessions;
    }
}
