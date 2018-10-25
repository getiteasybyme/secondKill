package com.secondKill.commom;

import com.secondKill.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池配置
 * 参数在properties里配置
 */
public class RedisPool {
    //保证连接池在tomcat启动的时候就加载出来
    //设置defaultValue 防止代码同步时被修改而报错
    //jedis连接池
    private static JedisPool pool;
    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
    //最大空闲实例个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));;
    //最小空闲实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));;
    //验证jedis实例是否是可用的 设置为true 则得到的jedis肯定是可用的
    private static boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));

    //如果实例不可用 则是表示 该链接调用过程中出现了异常
    //在并发非常高的情况下 因为源码里提供了返回到brokenResource的方法，为了提高了效率可以不做可用验证
    //归还jedis实例时验证是否可用 设置为true 则归还的jedis肯定可用
    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");

    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));

    public static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        //连接耗尽的时候 是否阻塞 false会抛出异常 true阻塞直到超时 默认为true
        config.setBlockWhenExhausted(true);

        //timeout单位是毫秒
        pool = new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static {
        initPool();
    }

    //获取jedis实例
    public static Jedis getJedis(){
        return pool.getResource();
    }

    //放jedis到连接池
    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("chenKey","chenValue");
        returnBrokenResource(jedis);
    }
}
