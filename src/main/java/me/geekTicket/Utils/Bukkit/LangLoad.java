package me.geekTicket.Utils.Bukkit;

import com.google.common.base.Joiner;
import me.geekTicket.GeekTicketMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LangLoad {
    private static FileConfiguration language;
    private static File lang;
    private static File getDataFolder() {
        return GeekTicketMain.instance.getDataFolder();
    }

    public static void loadlang() {
        saveLangConfig();
        lang = new File(new File(getDataFolder(),"lang"), "zh_CN.yml");
        ConfigManager.PLUGIN_NAME = getString("PLUGIN_NAME");
        ConfigManager.HELP = getStringList("HELP");
        ConfigManager.NO_PERM = getString("NO_PERM");
        ConfigManager.JOIN_SMG = getString("JOIN_SMG");
        ConfigManager.IS_MATCH = getString("ISMATCH");
        ConfigManager.IS_ONLINE = getString("ISONLINE");
        ConfigManager.CONSOLE_GIVE_SMG = getString("AC_MSG.GIVE");
        ConfigManager.CONSOLE_TAKE_SMG = getString("AC_MSG.TAKE");
        ConfigManager.PLAYER_GIVE_SMG = getStringsList("AC_MSG.OnGive");
        ConfigManager.PLAYER_TAKE_SMG = getStringsList("AC_MSG.OnTake");
    }

    public static FileConfiguration getLang() {
        language = YamlConfiguration.loadConfiguration(lang);
        return language;
    }
    private static String getString(String s) {
        return getLang().getString(s).replace("&", "§");
    }
    private static List<String> getStringList(String s) {
       return Arrays.asList(Joiner.on(",").join(getLang().getStringList(s)).replace("&", "§").split(","));
    }
    private static List<String> getStringsList(String s) {
        return Arrays.asList(getLang().getString(s).replace("&", "§").split("\n"));
    }
    private static void saveLangConfig() {
        File dir = new File(getDataFolder(),"lang");
        if (!dir.exists()) dir.mkdirs();
        File lang = new File(dir, "zh_CN.yml");
        if (!lang.exists()) {
            GeekTicketMain.instance.saveResource( "Lang/zh_CN.yml",false);
        }
    }
}
