package me.geekTicket;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

public class Language {

    //language
    public static List<String> getHelp() {
        List<String> h = ConfigManager.getLang().getStringList("HELP");
        String out = Joiner.on(",").join(h).replace("&","§");
        return Arrays.asList(out.split(","));
    }
    /**
     * 语言文件
     * @return
     */
    public final static String PLUGIN_NAME = getString("PLUGIN_NAME");
    public final static String NO_PERM = getString("NO_PERM");
    public final static String JOIN_SMG = getString("JOIN_SMG");
    public final static String ISMATCH = getString("ISMATCH");
    public final static String ISONLINE = getString("ISONLINE");

    public final static String GIVE = getString("AC_MSG.GIVE");
    public final static String TAKE = getString("AC_MSG.TAKE");
    public final static List<String> OnGive = getStrings("AC_MSG.OnGive");
    public final static List<String> OnTake = getStrings("AC_MSG.OnTake");



    private static String getString(String s) {
        return ConfigManager.getLang().getString(s).replace("&", "§");
    }
    private static List<String> getList(String s) {
       List<String> a = ConfigManager.getLang().getStringList(s);
       String b = Joiner.on(",").join(a).replace("&", "§");
       return Arrays.asList(b.split(","));
    }
    private static List<String> getStrings(String s) {
        String a = ConfigManager.getLang().getString(s).replace("&", "§");
        return Arrays.asList(a.split("\n"));
    }
}
