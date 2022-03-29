package me.geekTicket.Utils.Bukkit;
import me.geekTicket.GeekTicketMain;

import java.io.File;
import java.util.List;

public class ConfigManager {

    public static File getDataFolder() {
        return GeekTicketMain.instance.getDataFolder();
    }

    public static String USE_TYPE;
    public static String HOST;
    public static int PORT;
    public static String DATA_BASE;
    public static String USER_NAME;
    public static String PASS_WORD;
    public static String PARAMS;
    public static int MAXIMUM_POOL_SIZE;
    public static int MINIMUM_IDLE;
    public static int MAXIMUM_LIFETIME;
    public static int KEEPALIVE_TIME;
    public static int CONNECTION_TIMEOUT;
    public static int IDLE_TIMEOUT;
    public static boolean AUTO_CLEAR;
    public static int AUTO_CLEAR_DAY;


    //lang
    public static String PLUGIN_NAME;
    public static String NO_PERM;
    public static String JOIN_SMG;
    public static String IS_MATCH;
    public static String IS_ONLINE;
    public static String CONSOLE_GIVE_SMG;
    public static String CONSOLE_TAKE_SMG;
    public static List<String> PLAYER_GIVE_SMG;
    public static List<String> PLAYER_TAKE_SMG;

    public static List<String> HELP;


}
