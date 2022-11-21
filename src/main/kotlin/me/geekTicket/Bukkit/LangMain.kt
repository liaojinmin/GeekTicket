package me.geekTicket.Bukkit

import com.google.common.base.Joiner
import me.geekTicket.GeekTicketMain.instance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

class LangMain {
    private var lang: File? = null

    //lang
    companion object {
        @JvmStatic
        lateinit var PLUGIN_NAME: String
        @JvmStatic
        lateinit var NO_PERM: String
        @JvmStatic
        lateinit var JOIN_SMG: String
        @JvmStatic
        lateinit var IS_MATCH: String
        @JvmStatic
        lateinit var IS_ONLINE: String
        @JvmStatic
        lateinit var CONSOLE_GIVE_SMG: String
        @JvmStatic
        lateinit var CONSOLE_TAKE_SMG: String
        @JvmStatic
        lateinit var PLAYER_GIVE_SMG: List<String>
        @JvmStatic
        lateinit var PLAYER_TAKE_SMG: List<String>
        @JvmStatic
        lateinit var HELP: List<String>
    }

    init {
        saveLangConfig()
        lang = File(File(instance.dataFolder, "lang"), "zh_CN.yml")
        PLUGIN_NAME = getString("PLUGIN_NAME")
        HELP = getStringList("HELP")
        NO_PERM = getString("NO_PERM")
        JOIN_SMG = getString("JOIN_SMG")
        IS_MATCH = getString("ISMATCH")
        IS_ONLINE = getString("ISONLINE")
        CONSOLE_GIVE_SMG = getString("AC_MSG.GIVE")
        CONSOLE_TAKE_SMG = getString("AC_MSG.TAKE")
        PLAYER_GIVE_SMG = getStringsList("AC_MSG.OnGive")
        PLAYER_TAKE_SMG = getStringsList("AC_MSG.OnTake")
    }

    fun getLang(): FileConfiguration {
        return YamlConfiguration.loadConfiguration(lang!!)
    }

    private fun getString(s: String): String {
        return getLang().getString(s)!!.replace("&", "§")
    }

    private fun getStringList(s: String): List<String> {
        return Arrays.asList(
            *Joiner.on(",").join(getLang().getStringList(s)).replace("&", "§").split(",").toTypedArray()
        )
    }

    private fun getStringsList(s: String): List<String> {
        return Arrays.asList(*getLang().getString(s)!!.replace("&", "§").split("\n").toTypedArray())
    }

    private fun saveLangConfig() {
        val dir = File(instance.dataFolder, "lang")
        if (!dir.exists()) dir.mkdirs()
        val lang = File(dir, "zh_CN.yml")
        if (!lang.exists()) {
            instance.saveResource("lang/zh_CN.yml", false)
        }
    }

}