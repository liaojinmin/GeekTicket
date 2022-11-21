package me.geekTicket.Bukkit

import me.geekTicket.GeekTicketMain
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable

class BukkitEvent : Listener {
    @EventHandler
    fun onJoinUpdate(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        object : BukkitRunnable() {
            override fun run() {
                GeekTicketMain.action.getPlayerData(uuid)
                GeekTicketMain.say("加入游戏更新数据")
            }
        }.runTaskAsynchronously(GeekTicketMain.instance)
    }
    @EventHandler
    fun onQuitUpdate(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        object : BukkitRunnable() {
            override fun run() {
                GeekTicketMain.action.setPlayerData(uuid, GeekTicketMain.action.getPlayerTicket(uuid))
                GeekTicketMain.say("退出游戏游戏更新数据")
            }
        }.runTaskAsynchronously(GeekTicketMain.instance)
    }
}