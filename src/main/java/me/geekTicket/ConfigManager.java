package me.geekTicket;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static FileConfiguration config;
    private static FileConfiguration language;
    public static File getDataFolder() {
        return GeekTicketMain.instance.getDataFolder();
    }
    static final File yml = new File(getDataFolder(), "config.yml");
    static final File dir = new File(getDataFolder(),"lang");
    static final File lang = new File(dir, "zh_CN.yml");

    public static final int maximum_pool_size = getConfig().getInt("data_storage.hikari_settings.maximum_pool_size");
    public static final int minimum_idle = getConfig().getInt("data_storage.hikari_settings.minimum_idle");
    public static final int maximum_lifetime = getConfig().getInt("data_storage.hikari_settings.maximum_lifetime");
    public static final int keepalive_time = getConfig().getInt("data_storage.hikari_settings.keepalive_time");
    public static final int connection_timeout = getConfig().getInt("data_storage.hikari_settings.connection_timeout");
    public static final int idle_timeout = getConfig().getInt("data_storage.hikari_settings.idle_timeout");

    /**
     *
     * @return 获取配置文件目录
     */
    public static FileConfiguration getConfig() {
        config = YamlConfiguration.loadConfiguration(yml);
        return config;
    }
    /**
     *
     * @return 获取语言文件目录
     */
    public static FileConfiguration getLang() {
        language = YamlConfiguration.loadConfiguration(lang);
        return language;
    }


    public static void saveLangConfig() {
        File dir = new File(getDataFolder(),"lang");
        if (!dir.exists()) dir.mkdirs();
        File lang = new File(dir, "zh_CN.yml");
        if (!lang.exists()) {
            GeekTicketMain.instance.saveResource( "Lang/zh_CN.yml",false);
        }
    }
}
