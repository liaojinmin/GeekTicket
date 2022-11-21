package me.geekTicket.Utils.Menu

import com.google.common.base.Joiner
import me.geekTicket.GeekTicketMain.instance
import me.geekTicket.GeekTicketMain.say
import me.geekTicket.Utils.Data.Menu.MenuDataManage
import me.geekTicket.Obj.MenuButtonsObj
import me.geekTicket.Obj.MenuKey
import me.geekTicket.Obj.MenuSettingsObj
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class MenuConfigManage(MenuFileName: String) : MenuDataManage() {
    private var Menu: File? = null

    init {
        val stime = System.currentTimeMillis()
        LoadMenu(MenuFileName)
        say("§b加载菜单 §a" + MenuFileName + ".yml §b完成. §8(耗时" + (System.currentTimeMillis() - stime) + " ms)")
    }

    fun LoadMenu(MenuFileName: String) {
        Menu = File(File(instance.dataFolder, "menu"), "$MenuFileName.yml")
        val MenuID = Menu!!.name
        val MenuName = MenuID.substring(0, MenuID.indexOf("."))
        val data = MenuSettingsObj(title, shape, shapeByString, bindings)
        MenuSettings[MenuName] = data
        MenuCommand[bindings] = MenuName
        val var100 = menuConfig.getConfigurationSection("Buttons")!!.getKeys(false)
        val var300 = var100.toString()
            .replace("[", "")
            .replace("]", "")
            .replace(" ", "")
            .replace(",", "")
        var var201 = 0
        while (var201 < var100.size) {
            if (var300[var201] != '\u0000') {
                val IconID = var300[var201].toString()
                val var2000 = MenuButtonsObj(
                    getMats(IconID),
                    getName(IconID),
                    getLore(IconID),
                    getTicket(IconID),
                    getCommand(IconID),
                    getDeny(IconID)
                )
                MenuButtons[MenuKey(MenuName, IconID)] = var2000
                var201++
            }
        }
    }

    val title: String
        get() = menuConfig.getString("Title")?.replace("&", "§").toString()

    val shape: Array<String>
        get() = menuConfig.getStringList("Shape").toTypedArray()

    val shapeByString: String
        get() = Joiner.on("").join(menuConfig.getStringList("Shape"))

    val bindings: String
        get() = menuConfig.getString("Bindings.Commands") ?: "null"

    // 图标层 Buttons
    fun getMats(IconID: String): String {
        return menuConfig.getString("Buttons.$IconID.display.mats")!!
    }

    fun getName(IconID: String): String {
        return menuConfig.getString("Buttons.$IconID.display.name")?.replace("&", "§").toString()
    }

    fun getLore(IconID: String): List<String> {
        return listOf(*Joiner.on(",")
            .join(menuConfig.getStringList("Buttons.$IconID.display.lore"))
            .replace("&", "§")
            .split(",")
            .toTypedArray()
        )
    }

    fun getTicket(IconID: String): Int {
        return menuConfig.getInt("Buttons.$IconID.Ticket-Condition")
    }

    fun getDeny(IconID: String): String {
        return menuConfig.getString("Buttons.$IconID.deny")!!.replace("&", "§")
    }

    fun getCommand(IconID: String): List<String> {
        return menuConfig.getStringList("Buttons.$IconID.console-Command")
    }

    private val menuConfig: FileConfiguration
        get() = YamlConfiguration.loadConfiguration(Menu!!)

    companion object {
        @JvmStatic
        fun saveDefaultMenu() {
            val dir = File(instance.dataFolder, "menu")
            if (!dir.exists()) dir.mkdirs()
            val lang = File(dir, "default.yml")
            if (!lang.exists()) {
                instance.saveResource("menu/default.yml", false)
            }
        }
    }
}