package com.fantasy.config.redis;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jingc
 * @description RedisCacheService  封装redis常用方法
 * @since 2022/2/23
 */

@Service
public class RedisCacheService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T getCacheValue(final String key) {
        ValueOperations<String, T> stringObjectValueOperations = redisTemplate.opsForValue();
        return stringObjectValueOperations.get(key);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


}
