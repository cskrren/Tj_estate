package com.rkr.service;

import com.alibaba.fastjson.JSON;
import lombok.val;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public <T> T get(String key, Class<T> clazz) {
        String val= redisTemplate.opsForValue().get(key).toString();
        return JSON.parseObject(val, clazz);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value));
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void setHash(String key, Map<String, String> map) {
        if(key == null || map == null){
            return;
        }
        System.out.println(key);
        redisTemplate.opsForHash().putAll(key, map);
    }

    public  <T> List<T> getHash(String key, Class<T> clazz) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        List<T> list = new ArrayList<>();
        for (Object value : map.values()) {
            System.out.println(value.toString());
            list.add(JSON.parseObject(value.toString(), clazz));
        }
        return list;
    }

    public void setListFromRight(String key, List<String> list) {
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    public void setOneFromLeft(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    public Boolean canGet(String key, Integer end) {
        return redisTemplate.opsForList().size(key) > end;
    }
    public <T> List<T> getList(String key, Integer start, Integer end, Class<T> clazz) {
        List<Object> partList =  redisTemplate.opsForList().range(key, start, end);
        List<T> list = new ArrayList<>();
        for (Object value : partList) {
            list.add(JSON.parseObject(value.toString(), clazz));
        }
        return list;
    }

    public <T> T getOne(String key, String field, Class<T> clazz) {
        Object val = redisTemplate.opsForHash().get(key, field);
        return JSON.parseObject(val.toString(), clazz);
    }

    public void setOne(String key, String field, Object value) {
        if(value.getClass().equals(String.class)){
            redisTemplate.opsForHash().put(key, field, value);
            return;
        }
        redisTemplate.opsForHash().put(key, field, JSON.toJSONString(value));
    }

    public void deleteOne(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    public boolean hasHashKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public <T> List<T> getMulti(String key, List<String> fields, Class<T> clazz){
        List<String> result = redisTemplate.<String, String>opsForHash().multiGet(key, fields);
        List<T> list = new ArrayList<>();
        for (String val : result) {
            list.add(JSON.parseObject(val, clazz));
        }
        return list;
    }

    public void setMulti(String key, Map<String, Object> map) {
        Map<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            newMap.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
        }
        redisTemplate.opsForHash().putAll(key, newMap);
    }

    public void deleteMulti(String key, List<String> fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }
}
