package com.secondKill.util;

import com.secondKill.commom.RedisPool;
import com.secondKill.vo.SeckillInformationVo;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RedisPoolUtil {

    /**
     * 设置有效期 单位 秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("expire key;{} error", key,e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //exTime时间单位秒
    public static String setex(String key, String value,int exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,exTime,value);
        } catch (Exception e) {
            //log.error("setex key;{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 数据-1
     * @param productId
     * @param object
     * @param time
     * @return
     */
    public static String setexObject(Long productId, SeckillInformationVo object, Long time) {
        Jedis jedis = null;
        String result = null;
        //传进来的是个对象
        AtomicInteger val = new AtomicInteger(object.getInventory());
        if (val.intValue() <= 0){
            return "-1";
        }
        val.decrementAndGet();
        object.setInventory(val.intValue());
        String Id = String.valueOf(productId);
        Integer exTime = time.intValue()/1000;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(Id,exTime, JsonUtil.obj2String(object));
        } catch (Exception e) {
            //log.error("set key;{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            //log.error("set key;{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key;{} ", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("get key;{} ", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
}
