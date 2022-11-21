package me.geekTicket.BungeeCord

import me.geekTicket.GeekTicketBC.say
import me.geekTicket.GeekTicketBC.Actions
import me.geekTicket.Utils.Redis.SubRedis
import me.geekTicket.Utils.Redis.RedisMessages
import me.geekTicket.Configuration.ConfigManage
import me.geekTicket.GeekTicketBC

/**
 * @author : 老廖
 * @date : 2022-05-02 11:07
 */
class BungeeRedis : SubRedis() {
    init {
        onStart()
    }

    /**
     * 处理信息
     * @param message 传入的消息
     */
    override fun processMessage(message: RedisMessages) {
        if (message.target.equals("Bungee", ignoreCase = true)) {
            when (message.type) {
                "Server:Close" -> {

                    // DemoBug
                    if (ConfigManage.DEMO_BUG) {
                        say("&7&l与下游服务器: &f" + message.serverID + " &7&l断开链接.")
                    }
                    return
                }
                "Server:Start" -> {

                    // DemoBug
                    if (ConfigManage.DEMO_BUG) {
                        say("&7&l与下游服务器: &f" + message.serverID + " &7&l握手成功.")
                    }
                }
            }
        }
        if (message.target == "Bungee:Bukkit" && message.type == "Player:Quit") {
            say("接收退出 Bungee:Bukkit 消息")
            // 保存数据到数据库
            Actions.setPlayerData(message.uuid, message.ticket)
            // DemoBug
            if (ConfigManage.DEMO_BUG) {
                say("收到下游: " + message.serverID + " 发送的玩家，退出事件-Player:Quit")
                say("&e&l玩家UUID：&f " + message.uuid)
                //   GeekTicketBC.say("&e&l持有月券：&f " + message.getTicket());
            }
        }
    }
}