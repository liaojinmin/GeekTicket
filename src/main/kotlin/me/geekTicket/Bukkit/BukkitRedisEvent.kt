package me.geekTicket.Bukkit

import me.geekTicket.GeekTicketMain
import me.geekTicket.Utils.Redis.RedisMessages
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent


class BukkitRedisEvent: Listener {
    private val Prol = Bukkit.getServer().port.toString()

    @EventHandler(priority = EventPriority.LOWEST)
    fun onQuitUpdate(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        Bukkit.getScheduler().scheduleSyncDelayedTask(GeekTicketMain.instance,  {
            RedisMessages(
                "Player:Quit",
                "Bungee:Bukkit",
                GeekTicketMain.action.getPlayerTicket(uuid),
                uuid,
                Prol
            ).send()
        }, 5)
    }
}