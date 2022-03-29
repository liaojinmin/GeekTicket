package me.geekTicket.Utils.Bukkit;

import me.geekTicket.GeekTicketMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigLoad {

    private static FileConfiguration config;
    static File yml = new File(ConfigManager.getDataFolder(), "config.yml");

    public static void loadConfig() {
        //sql
        ConfigManager.USE_TYPE = getConfig().getString("data_storage.use_type");
        ConfigManager.HOST = getConfig().getString("data_storage.mysql.host");
        ConfigManager.PORT = getConfig().getInt("data_storage.mysql.port");
        ConfigManager.DATA_BASE = getConfig().getString("data_storage.mysql.database");
        ConfigManager.USER_NAME = getConfig().getString("data_storage.mysql.username");
        ConfigManager.PASS_WORD = getConfig().getString("data_storage.mysql.password");
        ConfigManager.PARAMS = getConfig().getString("data_storage.mysql.params");
        //sql cp
        ConfigManager.MAXIMUM_POOL_SIZE = getConfig().getInt("data_storage.hikari_settings.maximum_pool_size");
        ConfigManager.MINIMUM_IDLE = getConfig().getInt("data_storage.hikari_settings.minimum_idle");
        ConfigManager.MAXIMUM_LIFETIME = getConfig().getInt("data_storage.hikari_settings.maximum_lifetime");
        ConfigManager.KEEPALIVE_TIME = getConfig().getInt("data_storage.hikari_settings.keepalive_time");
        ConfigManager.CONNECTION_TIMEOUT = getConfig().getInt("data_storage.hikari_settings.connection_timeout");
        ConfigManager.IDLE_TIMEOUT = getConfig().getInt("data_storage.hikari_settings.idle_timeout");
        //task
        ConfigManager.AUTO_CLEAR = getConfig().getBoolean("auto_clear");
        ConfigManager.AUTO_CLEAR_DAY = getConfig().getInt("auto_clear_day");
    }

    /**
     *
     * @return 获取配置文件目录
     */
    public static FileConfiguration getConfig() {
        config = YamlConfiguration.loadConfiguration(yml);
        return config;
    }

}
