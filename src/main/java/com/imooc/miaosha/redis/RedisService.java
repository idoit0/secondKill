package com.imooc.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> boolean set(String key,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length()<=0){
                return false;
            }
            jedis.set(key,str);
            return true;
        }finally{
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == String.class){
            return (String)value;
        }
        if(clazz == int.class || clazz == Integer.class){
            return value+"";
        }
        if(clazz == long.class || clazz == Long.class){
            return value+"";
        }
        return JSONObject.toJSONString(value);
    }

    public <T> T get(String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str,clazz);
            return t;
        }finally{
            returnToPool(jedis);
        }
    }

    private <T> T stringToBean(String str,Class<T> clazz) {
        if(str == null){
            return null;
        }
        if(clazz == String.class){
            return (T) str;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        }
        if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(str);
        }
        return JSON.toJavaObject(JSON.parseObject(str),clazz);
    }

    public void returnToPool(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }


}
