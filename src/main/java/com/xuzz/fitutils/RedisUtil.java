package com.xuzz.fitutils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import redis.clients.jedis.*;

/**
 * Created by win10 on 2017/6/21.
 */
public class RedisUtil {
    private static Logger logger = LogManager.getLogger(RedisUtil.class);
    private static final long serialVersionUID = -1149678082569464779L;
    private static String addr;
    private static int port;
    private static String auth;
    private static int maxActive;
    private static int maxIdle;
    private static int maxWait;
    private static int timeOut;
    private static boolean testOnBorrow;
    public static Jedis jedis;
    public static JedisPool jedisPool;
    public static ShardedJedis shardedJedis;
    public static ShardedJedisPool shardedJedisPool;

    public RedisUtil() {
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        auth = auth;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        maxWait = maxWait;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        timeOut = timeOut;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        testOnBorrow = testOnBorrow;
    }

    private static synchronized void init() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(150);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setMaxWaitMillis((long)maxWait);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(500L);
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1000L);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(1000L);
        jedisPoolConfig.setNumTestsPerEvictionRun(100);
        jedisPool = new JedisPool(jedisPoolConfig, addr, port, timeOut, (String)null, 0);
    }

    private static Jedis getJedis() {
        try {
            if(jedisPool != null) {
                jedis = jedisPool.getResource();
            } else {
                init();
                jedis = jedisPool.getResource();
            }
        } catch (Exception var1) {
            logger.error("获取Redis实例出错，" + var1);
        }

        return jedis;
    }

    public static String set(String key, String value) {
        return set(key, value, (Integer)null);
    }

    public static String set(String key, String value, Integer timeout) {
        String result = null;
        Jedis jedis = getJedis();
        if(jedis == null) {
            return result;
        } else {
            try {
                result = jedis.set(key, value);
                if(null != timeout) {
                    jedis.expire(key, timeout.intValue());
                }
            } catch (Exception var9) {
                logger.error(var9.getMessage(), var9);
            } finally {
                if(null != jedis) {
                    jedis.close();
                }

            }

            return result;
        }
    }

    public static String get(String key) {
        String result = null;
        Jedis jedis = getJedis();
        if(jedis == null) {
            return result;
        } else {
            try {
                result = jedis.get(key);
            } catch (Exception var7) {
                logger.error(var7.getMessage(), var7);
            } finally {
                if(null != jedis) {
                    jedis.close();
                }

            }

            return result;
        }
    }

    public static boolean del(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = getJedis();
        if(null == jedis) {
            return Boolean.FALSE.booleanValue();
        } else {
            try {
                jedis.del(key);
            } catch (Exception var7) {
                logger.error("删除redis数据出错，" + var7);
            } finally {
                if(null != jedis) {
                    jedis.close();
                }

            }

            return result.booleanValue();
        }
    }

    public static Long append(String key, String value) {
        Long result = Long.valueOf(0L);
        Jedis jedis = getJedis();
        if(null == jedis) {
            return result;
        } else {
            try {
                result = jedis.append(key, value);
            } catch (Exception var8) {
                logger.error("追加redis数据出错，" + var8);
            } finally {
                if(null != jedis) {
                    jedis.close();
                }

            }

            return result;
        }
    }

    public static Boolean exists(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = getJedis();
        if(null == jedis) {
            return result;
        } else {
            try {
                result = jedis.exists(key);
            } catch (Exception var7) {
                logger.error("检查是否存在出错：，" + var7);
            } finally {
                if(null != jedis) {
                    jedis.close();
                }

            }

            return result;
        }
    }
}
