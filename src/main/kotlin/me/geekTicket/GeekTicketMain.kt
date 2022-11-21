package me.geekTicket

import me.geekTicket.Action.ActionManage
import me.geekTicket.Bukkit.*
import me.geekTicket.Configuration.ConfigManage
import me.geekTicket.Metrics.Metrics
import me.geekTicket.Utils.Data.DataBaseManager
import me.geekTicket.Utils.Data.Menu.MenuDataManage
import me.geekTicket.Utils.Menu.MenuAction
import me.geekTicket.Utils.Placeholder.Papi_Hook
import me.geekTicket.Utils.Redis.RedisMessages
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import taboolib.common.env.DependencyScope
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.platform.BukkitPlugin
import java.util.*


@RuntimeDependencies(
    RuntimeDependency(value = "redis.clients:jedis:4.2.2",
        test = "redis.clients.jedis.exceptions.JedisException",
        transitive = false, ignoreOptional = true, scopes = [DependencyScope.PROVIDED]
    ),

    RuntimeDependency(value = "!com.zaxxer:HikariCP:4.0.3",
        transitive = false, ignoreOptional = true, scopes = [DependencyScope.PROVIDED],
        relocate = ["!com.zaxxer.hikari", "!com.zaxxer.hikari_4_0_3"]
    ),

    RuntimeDependency(value = "org.apache.commons:commons-pool2:2.11.1",
        test = "org.apache.commons.pool2.impl.GenericObjectPoolConfig",
        transitive = false, ignoreOptional = true, scopes = [DependencyScope.PROVIDED],
        repository = "https://repo.alessiodp.com/releases/"
    ),

    RuntimeDependency(value = "mysql:mysql-connector-java:8.0.27",
        test = "com.mysql.cj.jdbc.Driver",
        transitive = false, ignoreOptional = true, scopes = [DependencyScope.PROVIDED]
    ),
    RuntimeDependency(value = "org.xerial:sqlite-jdbc:3.36.0.3",
        test = "org.sqlite.SQLiteDataSource",
        transitive = false, ignoreOptional = true, scopes = [DependencyScope.PROVIDED]
    )
)
object GeekTicketMain : Plugin() {

    val instance by lazy { BukkitPlugin.getInstance() }
    const val Version = "2.03"
    @JvmStatic
    val action = ActionManage()

    override fun onLoad() {
        instance.saveDefaultConfig()
        motd("")
        motd("正在加载 §3§lGeekTicket §f...  §8")
        motd("")
        ConfigMain()
        LangMain()
    }

    override fun onEnable() {
        val stime = System.currentTimeMillis()
        motd("  ________               __   ___________.__        __           __  ")
        motd(" /  _____/  ____   ____ |  | _\\__    ___/|__| ____ |  | __ _____/  |_ ")
        motd("/   \\  ____/ __ \\_/ __ \\|  |/ / |    |   |  |/ ___\\|  |/ // __ \\   __\\")
        motd("\\    \\_\\  \\  ___/\\  ___/|    <  |    |   |  \\  \\___|    <\\  ___/|  | ")
        motd(" \\______  /\\___  >\\___  >__|_ \\ |____|   |__|\\___  >__|_ \\\\___  >__|  ")
        motd("        \\/     \\/     \\/     \\/                  \\/     \\/    \\/      ")
        motd("")
        motd("     §2GeekTicket §bv$Version §7by §2www.geekcraft.ink")
        motd("     §8适用于Bukkit: §71.7.10-1.18.1 §8当前: §8" + instance.server.name)
        motd("")

        MenuDataManage.LoadAllMenu()

        Metrics(instance, 14521)

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            say("§8找到 §7PlaceholderAPI §8启用变量功能")
            Papi_Hook().register()
        }

        if (ConfigManage.START_BUNGEE) {
            // 启动 Bukkit Redis
            BukkitRedis()
            // 发送 Redis 启动消息
            onStartRedis()
            // 注册 Bukkit Redis 事件监听
            BukkitPlugin.getInstance().server.pluginManager.registerEvents(BukkitRedisEvent(), instance)
            // 启动本地排行榜计算任务
            StartModule.Task.getLocalTop()
           // CheckUpdate()
            say("&6&l已启用 §7Redis BungeeCord &6&l模式 将不再链接储存库！")
        } else {
            // 正常启动数据库管理器
            DataBaseManager()
            // 自动任务启动逻辑
            StartModule.onStart()
            // 注册 Bukkit 事件
            BukkitPlugin.getInstance().server.pluginManager.registerEvents(BukkitEvent(), instance)
           // CheckUpdate()
        }
        say("§b启动完成 §8(耗时" + (System.currentTimeMillis() - stime) + " ms)")
    }

    override fun onDisable() {
        // 不是 BC 运行环境时启动
        if (!ConfigManage.START_BUNGEE) {
            // 插件卸载保存数据
            StartModule.Task.onActiveUpdate()
            // 关闭数据库
            DataBaseManager.closeData()
            // 关闭所有GUI
            MenuAction.CloseGui()
            // 注销所有事件
            HandlerList.unregisterAll(instance)
            say("§a再见！  ")
            return
        }
        // 关闭所有GUI
        MenuAction.CloseGui()
        // 注销所有事件
        HandlerList.unregisterAll(instance)
        onCloseRedis()
        say("§a再见！  ")
    }


    private fun onStartRedis() {
        RedisMessages("Server:Start",
            "Bungee",
            0,
            UUID.randomUUID(),
            Bukkit.getServer().port.toString()).send()
    }
    private fun onCloseRedis() {
        RedisMessages("Server:Close",
            "Bungee",
            0,
            UUID.randomUUID(),
            Bukkit.getServer().port.toString()).send()
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