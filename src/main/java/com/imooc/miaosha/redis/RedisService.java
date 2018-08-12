package com.imooc.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service
public class RedisService{

    @Autowired
    JedisPool jedisPool;

    /**
     * 设置对象
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str == null || str.length()<=0){
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if(seconds <= 0){
                jedis.set(realKey,str);
            }else{
                jedis.setex(realKey,seconds,str);
            }
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

    /**
     * 获取单个对象
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String readKey = prefix.getPrefix() + key;
            String str = jedis.get(readKey);
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

    /**
     * 判断是否已经存在Key
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public<T> boolean exists(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String readKey = prefix.getPrefix() + key;
            return jedis.exists(readKey);
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public<T> Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String readKey = prefix.getPrefix() + key;
            return jedis.incr(readKey);
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public<T> Long decr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String readKey = prefix.getPrefix() + key;
            return jedis.decr(readKey);
        }finally{
            returnToPool(jedis);
        }
    }



}
