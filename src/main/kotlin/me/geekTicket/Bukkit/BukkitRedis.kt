package me.geekTicket.Bukkit

import me.geekTicket.Action.ActionManage
import me.geekTicket.Configuration.ConfigManage
import me.geekTicket.GeekTicketMain.say
import me.geekTicket.Obj.TicketObj
import me.geekTicket.Utils.Redis.RedisMessages
import me.geekTicket.Utils.Redis.SubRedis
import org.bukkit.Bukkit

/**
 * @author : 老廖
 * @date : 2022-05-02 17:28
 */
class BukkitRedis : SubRedis() {
    init {
        onStart()
    }

    override fun processMessage(message: RedisMessages) {
        if (message.target.equals("Bukkit", ignoreCase = true) && message.type.equals("Player:Con", ignoreCase = true)) {
            // DemoBug
            if (ConfigManage.DEMO_BUG) {
                say("收到上游: " + message.serverID + " 发送的玩家，链接代理-Player:Con")
                say("&e&l玩家UUID：&f " + message.uuid)
               // say("&e&l持有月券：&f " + message.ticket)
            }
            val name = Bukkit.getOfflinePlayer(message.uuid).name
            if (name != null) {
                ActionManage.TicketHashMap[message.uuid] = TicketObj(name, message.uuid, message.ticket)
            }
            return
        }
        if (message.target.equals("Bungee:Bukkit", ignoreCase = true) && message.type.equals("Player:Quit", ignoreCase = true)) {
            val name = Bukkit.getOfflinePlayer(message.uuid).name
            if (name != null) {
                ActionManage.TicketHashMap[message.uuid] = TicketObj(name, message.uuid, message.ticket)
            }
        }
    }
}