package me.geekTicket

import me.geekTicket.Action.ActionBungee
import me.geekTicket.BungeeCord.BungeeRedis
import me.geekTicket.BungeeCord.ConfigMain
import me.geekTicket.Utils.Data.DataBaseManager
import taboolib.common.platform.Platform
import taboolib.common.platform.PlatformSide
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.platform.BungeePlugin

/**
 * @author : 老廖
 * @date : 2022-05-01 20:30
 */
@PlatformSide([Platform.BUNGEE])
object GeekTicketBC : Plugin() {

    val instance by lazy { BungeePlugin.getInstance() }
    @JvmStatic
    val Actions = ActionBungee()

    override fun onLoad() {
        motd("  ________               __   ___________.__        __           __  ")
        motd(" /  _____/  ____   ____ |  | _\\__    ___/|__| ____ |  | __ _____/  |_ ")
        motd("/   \\  ____/ __ \\_/ __ \\|  |/ / |    |   |  |/ ___\\|  |/ // __ \\   __\\")
        motd("\\    \\_\\  \\  ___/\\  ___/|    <  |    |   |  \\  \\___|    <\\  ___/|  | ")
        motd(" \\______  /\\___  >\\___  >__|_ \\ |____|   |__|\\___  >__|_ \\\\___  >__|  ")
        motd("        \\/     \\/     \\/     \\/                  \\/     \\/    \\/      ")
        motd("")
        motd("     §6§lGeekTicket §bv${GeekTicketMain.Version} §7by §2www.geekcraft.ink")
        motd("     §8启动 §7BungeeCord §8管理模式")
        motd("")
    }

    override fun onEnable() {
        // 配置加载
        ConfigMain()
        // 数据库管理器加载
        DataBaseManager()
        // Redis管理器加载
        BungeeRedis()
        // 监听事件注册
    }

    @JvmStatic
    fun motd(s: String) {
        console().sendMessage(s)
    }

    @JvmStatic
    fun say(s: String) {
        console().sendMessage("§8[§3§lGeekTicket§8] ${s.replace("&", "§")}")
    }
}