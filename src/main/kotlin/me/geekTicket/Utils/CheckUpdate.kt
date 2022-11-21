package me.geekTicket.Utils

import me.geekTicket.GeekTicketMain
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.HashMap

class CheckUpdate {
    init {
        try {
            val url = URL("https://api.spigotmc.org/legacy/update.php?resource=" + RESOURCE)
            val u = url.openConnection()
            val NewVersion = BufferedReader(InputStreamReader(u.getInputStream())).readLine()
            getNewVersion["Int"] = NewVersion.replace("v", "").replace(".", "")
            getNewVersion["New"] = NewVersion.replace("v", "")
            check()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun check() {
        if (getNewVersion["Int"]!!.toInt() > GeekTicketMain.Version.replace(".", "").toInt()) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §8发现新版本待更新, 当前版本:§f " + GeekTicketMain.Version + " §8最新版本:§f " + getNewVersion["New"])
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §8Found a new version to be updated, the current version:§f " + GeekTicketMain.Version + " §8New:§f " + getNewVersion["New"])
        } else {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §8你使用的是最新版本§f " + GeekTicketMain.Version)
        }
    }

    companion object {
        private const val RESOURCE = 100269
        var getNewVersion : MutableMap<String, String> = HashMap()
    }
}