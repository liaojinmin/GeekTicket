package me.geekTicket.Bukkit

import me.geekTicket.Configuration.ConfigManage
import me.geekTicket.GeekTicketMain.instance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author : 老廖
 * @date : 2022-05-02 13:27
 */
class ConfigMain {
    private val yml: File

    init {
        yml = File(instance.dataFolder, "config.yml")
        ConfigManage.DEMO_BUG = config.getBoolean("demo_bug", false)
        // MYSQL
        ConfigManage.DATA_TYPE = config.getString("data_storage.use_type", "sqlite")
        ConfigManage.MYSQL_HOST = config.getString("data_storage.mysql.host", "127.0.0.1")
        ConfigManage.MYSQL_PORT = config.getInt("data_storage.mysql.port", 3306)
        ConfigManage.MYSQL_DATABASE = config.getString("data_storage.mysql.database", "server_ticket")
        ConfigManage.MYSQL_USERNAME = config.getString("data_storage.mysql.username", "root")
        ConfigManage.MYSQL_PASSWORD = config.getString("data_storage.mysql.password", "123456")
        ConfigManage.MYSQL_PARAMS = config.getString("data_storage.mysql.params", "?autoReconnect=true&useSSL=false")
        ConfigManage.MYSQL_DATA_NAME = config.getString("data_storage.mysql.DataName", "roll_data")

        // hikari
        ConfigManage.MAXIMUM_POOL_SIZE = config.getInt("data_storage.hikari_settings.maximum_pool_size", 10)
        ConfigManage.MINIMUM_IDLE = config.getInt("data_storage.hikari_settings.minimum_idle", 5)
        ConfigManage.MAXIMUM_LIFETIME = config.getInt("data_storage.hikari_settings.maximum_lifetime", 1800000)
        ConfigManage.KEEPALIVE_TIME = config.getInt("data_storage.hikari_settings.keepalive_time", 0)
        ConfigManage.CONNECTION_TIMEOUT = config.getInt("data_storage.hikari_settings.connection_timeout", 5000)

        // RedisServer
        ConfigManage.START_BUNGEE = config.getBoolean("RedisServer.startBungee", false)
        ConfigManage.REDIS_HOST = config.getString("RedisServer.Redis_Set.host", "127.0.0.1")
        ConfigManage.REDIS_PORT = config.getInt("RedisServer.Redis_Set.port", 6379)
        ConfigManage.REDIS_PASSWORD = config.getString("RedisServer.Redis_Set.password", "")
        ConfigManage.REDIS_SSL = config.getBoolean("RedisServer.Redis_Set.use_ssl", false)

        // AutoTask
        ConfigManage.AUTO_CLEAR = config.getBoolean("auto_clear", false)
        ConfigManage.AUTO_CLEAR_DAY = config.getInt("auto_clear_day", 1)
        ConfigManage.GLOBAL_TOP = config.getBoolean("GlobalTop", false)
        ConfigManage.AUTO_SAVE = config.getBoolean("AutoSave", false)
    }

    val config: FileConfiguration
        get() = YamlConfiguration.loadConfiguration(yml)
}