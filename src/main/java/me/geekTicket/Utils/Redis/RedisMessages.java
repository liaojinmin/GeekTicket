package me.geekTicket.Utils.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.UUID;


/**
 * @author : 老廖
 * @date : 2022-05-02 10:30
 **/

public class RedisMessages {

    public static String CHANNEL = "GeekTicket";


    private final String Type;
    private final String Target;
    private final int Ticket;
    private final UUID Uuid;
    private final String ServerID;


    /**
     * 打包创建一个新的 Redis 消息
     * @param type 处理类型
     * @param target 目标
     * @param ticket 月券数量
     */
    public RedisMessages(String type, String target, int ticket, UUID uuid, String serverid) {
        this.Type = type;
        this.Target = target;
        this.Ticket = ticket;
        this.Uuid = uuid;
        this.ServerID = serverid;
    }

    /**
     * 处理传入的 Redis 消息
     */
    public RedisMessages(String msg) {
        String[] var100 = msg.split(";");
        Type = var100[0];
        Target = var100[1];
        Ticket = Integer.parseInt(var100[2]);
        Uuid = UUID.fromString(var100[3]);
        ServerID = var100[4];
    }

    /**
     * 发送redis消息
     */
    public void send() {
        try (Jedis publisher = SubRedis.getRedisConnection()){
            String msg = Type + ";" + Target + ";" + Ticket + ";" + Uuid + ";" + ServerID;
            publisher.publish(CHANNEL, msg);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        }
    }

    public String getType() { return Type; }
    public String getTarget() { return Target; }
    public int getTicket() { return Ticket; }
    public UUID getUuid() { return Uuid; }
    public String getServerID() { return ServerID; }
}
