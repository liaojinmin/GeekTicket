package me.geekTicket.BungeeCord

import me.geekTicket.GeekTicketBC.Actions
import net.md_5.bungee.api.event.PostLoginEvent
import me.geekTicket.Utils.Redis.RedisMessages
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import java.io.IOException

/**
 * @author : 老廖
 * @date : 2022-05-02 19:24
 */
object BungeeRedisEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onJoinServer(event: PostLoginEvent) {
        try {
            val uuid = event.player.uniqueId
            RedisMessages(
                "Player:Con","Bukkit",Actions.getPlayerData(uuid),uuid,"25565").send()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}