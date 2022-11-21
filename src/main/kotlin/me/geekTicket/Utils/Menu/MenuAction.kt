package me.geekTicket.Utils.Menu

import com.google.common.base.Joiner

import me.geekTicket.GeekTicketMain.action
import me.geekTicket.GeekTicketMain.instance
import me.geekTicket.Utils.Data.Menu.MenuDataManage
import me.geekTicket.Obj.MenuKey
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import java.util.concurrent.ConcurrentHashMap

/**
 * @author : 老廖
 * @date : 2022-04-29 20:28
 */
object MenuAction {
    var isOpenInventory: MutableMap<Player, String?> = ConcurrentHashMap()
    var inv: Inventory? = null
    fun CloseGui() {
        for (value in Bukkit.getOnlinePlayers()) {
            val player = value.player
            if (player != null) {
                if (isOpenInventory[player] != null) {
                    player.closeInventory()
                }
            }
        }
    }

    /**
     *
     * @param player 玩家对象
     * @param MenuId 菜单名称
     */
    fun open(player: Player, MenuId: String?) {
        BuildInventory(player, MenuId)
        player.openInventory(inv!!)
        isOpenInventory[player] = MenuId
        Bukkit.getPluginManager().registerEvents(object : Listener {
            @EventHandler
            fun onClick(e: InventoryClickEvent) {
                e.isCancelled = true
                if (e.inventory.type == InventoryType.PLAYER || e.rawSlot < 0 || e.rawSlot >= inv!!.size) {
                    return
                }
                val var100 = MenuDataManage.MenuSettings[MenuId]
                val IconID: String
                try {
                    if (var100!!.shape_String[e.rawSlot] != '\u0000' && e.whoClicked.openInventory.title == var100.title) {
                        IconID = var100.shape_String[e.rawSlot].toString()
                        val var200 = MenuDataManage.MenuButtons[MenuKey(MenuId!!, IconID)]
                        if (var200 != null) {
                            if (action.isTicket(player, var200.ticket)) {
                                action.onTakePlayerTicket(player.uniqueId, var200.ticket)
                                val T = Joiner.on(",").join(var200.command).replace("%player_name%", player.name)
                                val run = T.split(",").toTypedArray()
                                for (out in run) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), out)
                                }
                            } else {
                                player.sendMessage(var200.deny)
                            }
                        }
                    }
                } catch (ignored: StringIndexOutOfBoundsException) {
                }
            }

            @EventHandler
            fun onDrag(e: InventoryDragEvent) {
                if (player == e.whoClicked) {
                    e.isCancelled = true
                }
            }

            @EventHandler
            fun onClose(e: InventoryCloseEvent) {
                player.updateInventory()
                if (player == e.player) {
                    isOpenInventory.remove(player)
                    HandlerList.unregisterAll(this)
                }
            }
        }, instance)
    }
}