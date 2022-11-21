package me.geekTicket.BungeeCord;

import me.geekTicket.GeekTicketBC;
import me.geekTicket.Configuration.ConfigManage;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;

/**
 * @author : 老廖
 * @date : 2022-05-02 08:52
 **/
public final class ConfigMain {

    private final File ConfigPath;


    public ConfigMain() {
        File dataPath = new File(new File(System.getProperty("user.dir"), "plugins"), "GeekTicket");
        if (!dataPath.exists()) dataPath.mkdirs();
        ConfigPath = new File(dataPath, "config.yml");
        SaveDefaultConfig();
        try {

            ConfigManage.DEMO_BUG = getConfig().getBoolean("demo_bug", false);
            // MYSQL
            ConfigManage.DATA_TYPE = getConfig().getString("data_storage.use_type", "sqlite");
            ConfigManage.MYSQL_HOST = getConfig().getString("data_storage.mysql.host", "127.0.0.1");
            ConfigManage.MYSQL_PORT = getConfig().getInt("data_storage.mysql.port", 3306);
            ConfigManage.MYSQL_DATABASE = getConfig().getString("data_storage.mysql.database", "server_ticket");
            ConfigManage.MYSQL_USERNAME = getConfig().getString("data_storage.mysql.username", "root");
            ConfigManage.MYSQL_PASSWORD = getConfig().getString("data_storage.mysql.password", "123456");
            ConfigManage.MYSQL_PARAMS = getConfig().getString("data_storage.mysql.params", "?autoReconnect=true&useSSL=false");
            ConfigManage.MYSQL_DATA_NAME = getConfig().getString("data_storage.mysql.DataName", "roll_data");

            // hikari
            ConfigManage.MAXIMUM_POOL_SIZE = getConfig().getInt("data_storage.hikari_settings.maximum_pool_size", 10);
            ConfigManage.MINIMUM_IDLE = getConfig().getInt("data_storage.hikari_settings.minimum_idle", 5);
            ConfigManage.MAXIMUM_LIFETIME = getConfig().getInt("data_storage.hikari_settings.maximum_lifetime", 1800000);
            ConfigManage.KEEPALIVE_TIME = getConfig().getInt("data_storage.hikari_settings.keepalive_time", 0);
            ConfigManage.CONNECTION_TIMEOUT = getConfig().getInt("data_storage.hikari_settings.connection_timeout", 5000);

            // RedisServer
            ConfigManage.START_BUNGEE = getConfig().getBoolean("RedisServer.startBungee", false);
            ConfigManage.REDIS_HOST = getConfig().getString("RedisServer.Redis_Set.host", "127.0.0.1");
            ConfigManage.REDIS_PORT = getConfig().getInt("RedisServer.Redis_Set.port", 6379);
            ConfigManage.REDIS_PASSWORD = getConfig().getString("RedisServer.Redis_Set.password", "");
            ConfigManage.REDIS_SSL = getConfig().getBoolean("RedisServer.Redis_Set.use_ssl", false);

            // AutoTask
            ConfigManage.AUTO_CLEAR = getConfig().getBoolean("auto_clear", false);
            ConfigManage.AUTO_CLEAR_DAY = getConfig().getInt("auto_clear_day", 1);
            ConfigManage.GLOBAL_TOP = getConfig().getBoolean("GlobalTop", false);
            ConfigManage.AUTO_SAVE = getConfig().getBoolean("AutoSave", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(ConfigPath);
    }
    public void SaveDefaultConfig()  {
        if (ConfigPath.exists()) return;
        try (InputStream out = GeekTicketBC.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (out != null) {
                Files.copy(out, ConfigPath.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
