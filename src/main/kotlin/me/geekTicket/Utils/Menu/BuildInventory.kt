package me.geekTicket.Utils.Menu


import me.geekTicket.Utils.Data.Menu.MenuDataManage
import me.geekTicket.Obj.MenuButtonsObj
import me.geekTicket.Obj.MenuKey
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*

class BuildInventory(player: Player?, MenuId: String?) : MenuDataManage() {
    private lateinit var inventory: Inventory
    private val itemStack = ItemStack(Material.AIR)

    init {
        BuildGui(player, MenuId)
        MenuAction.inv = inventory
    }

    private fun BuildGui(player: Player?, MenuID: String?) {
        val var1 = MenuSettings[MenuID]
        val size = var1!!.shape_Array.size * 9
        val shape = var1.shape_String
        inventory = Bukkit.createInventory(player, size, var1.title)
        try {
            var A = 0
            while (A < size) {
                if (shape[A] != '\u0000') {
                    val IconID = shape[A].toString()
                    inventory.setItem(A, BuildDisplay(MenuID, IconID))
                    A++
                } else {
                    inventory.setItem(A, itemStack)
                }
            }
        } catch (ignored: StringIndexOutOfBoundsException) {
        }
    }

    private fun BuildDisplay(MenuID: String?, IconID: String): ItemStack? {
        val var2 = MenuButtons[MenuKey(MenuID!!, IconID)]
        var itemStack: ItemStack? = null
        if (var2 != null) {
            itemStack = ItemStack(Material.valueOf(var2.material.uppercase(Locale.getDefault())))
            itemStack.itemMeta = BuildStack(var2, itemStack)
        }
        return itemStack
    }

    private fun BuildStack(menuButtonsObj: MenuButtonsObj, itemStack: ItemStack): ItemMeta {
        val var100 = itemStack.itemMeta
        var100.setDisplayName(menuButtonsObj.name)
        var100.lore = menuButtonsObj.getlore()
        return var100
    }
}