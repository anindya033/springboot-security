package com.crafter.security.redisplayground.redisservice;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // --------------------
    // STRING operations
    // --------------------

    // Save value with TTL (optional)
    public void save(String key, Object value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
    }

    // Save without TTL
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // Get value
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete key
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    // --------------------
    // LIST operations
    // --------------------

    public void pushToList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public Object popFromList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public List<Object> getList(String key) {
        Long size = redisTemplate.opsForList().size(key);
        if (size == null || size == 0) return List.of();
        return redisTemplate.opsForList().range(key, 0, size - 1);
    }

    // --------------------
    // SET operations
    // --------------------

    public void addToSet(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public boolean isMemberOfSet(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    // --------------------
    // HASH operations (like Map)
    // --------------------

    public void putToHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getFromHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public void deleteFromHash(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    // --------------------
    // SORTED SET operations (ZSET)
    // --------------------

    public void addToSortedSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> getSortedSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }
}
