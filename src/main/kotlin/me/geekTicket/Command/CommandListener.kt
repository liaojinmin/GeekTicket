package me.geekTicket.Command

import me.geekTicket.Utils.Menu.MenuAction
import me.geekTicket.Utils.Data.Menu.MenuDataManage
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent


object CommandListener {

    @SubscribeEvent(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        val player = e.player
        val message = e.message.removePrefix("/")

        if (message.isNotBlank()) {

            if (!MenuDataManage.MenuCommand.isEmpty()) {
                if (MenuDataManage.MenuCommand.get(message) != null) {
                    MenuAction.open(player, MenuDataManage.MenuCommand.get(message))
                    e.isCancelled = true
                    return
                }
            }
        }
    }
}