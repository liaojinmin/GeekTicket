package me.geekTicket.Utils.Redis;

import me.geekTicket.Configuration.ConfigManage;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author : 老廖
 * @date : 2022-05-02 08:44
 **/
public abstract class SubRedis {

    private static JedisPool jedisPool;
    public boolean isActive;
    private static Logger logger;

    public SubRedis() {
        logger = Logger.getLogger("GeekTicket");
        if (ConfigManage.REDIS_PASSWORD.isEmpty()) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            jedisPool = new JedisPool(poolConfig,
                    ConfigManage.REDIS_HOST,
                    ConfigManage.REDIS_PORT,
                    5000 ,
                    ConfigManage.REDIS_SSL);
        } else {
            jedisPool = new JedisPool(new JedisPoolConfig(),
                    ConfigManage.REDIS_HOST,
                    ConfigManage.REDIS_PORT,
                    5000 ,
                    ConfigManage.REDIS_PASSWORD,
                    ConfigManage.REDIS_SSL);
        }

    }

    public abstract void processMessage(RedisMessages message);

    public static Jedis getRedisConnection() {
        return jedisPool.getResource();
    }
    public final void onStart() {
        new Thread(() -> {
            try (Jedis jedis = getRedisConnection()) {
                if (jedis.isConnected()) {
                    isActive = true;
                    logger.log(Level.INFO,"§f成功启用 Redis 服务器");
                } else {
                    isActive = false;
                    logger.log(Level.WARNING,"§f无法与 Redis 服务器建立连接");
                    return;
                }

                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        if (!channel.equals(RedisMessages.CHANNEL)) {
                            return;
                        }
                        processMessage(new RedisMessages(message));
                    }
                }, RedisMessages.CHANNEL);

            } catch (JedisException | IllegalStateException e) {
                logger.log(Level.WARNING,"§f无法与 Redis 服务器建立连接-JedisException");
                isActive = false;
            }
        }, "GeekTicket Redis").start();
    }
}
