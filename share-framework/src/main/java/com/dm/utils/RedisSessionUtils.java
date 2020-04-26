package com.dm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisSessionUtils {
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier("redisSessionTemplate")
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisSessionUtils.redisTemplate = redisTemplate;
    }

    /**
     * flushDB
     * */
    public static void flushDB() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return "OK";
            }
        });
    }

    /**
     * del by key
     * */
    public static boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * set expire timeout
     * */
    public static boolean expire(String key, long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    /**
     * rename key
     * */
    public static void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * get data type
     * */
    public static DataType dataType(String key) {
        return redisTemplate.type(key);
    }

    /**
     * has key
     * */
    public static boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * get keys
     * */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * operate String
     * */
    public static void set(String key, Object value) {
        System.out.println(redisTemplate);
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value);
        expire(key, timeout, unit);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static void multiSet(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    public static List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * operate list
     * */
    public static Long lRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public static Long lLeftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public static List<Object> range(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * operate set
     * */
    public static Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public static Set<Object> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * set operation
     * */

    /**
     * hash
     * */
    public static void hashPut(String id, String key, Object value) {
        redisTemplate.opsForHash().put(id, key, value);
    }

    public static void hashPutAll(String id, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(id, map);
    }
}
