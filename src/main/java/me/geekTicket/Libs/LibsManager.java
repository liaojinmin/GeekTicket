package me.geekTicket.Libs;

import me.geekTicket.GeekTicketMain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class LibsManager {

    public static Map<String, String> getBukkitVersion = new HashMap<>();
    static final String httpUrl = "https://plugins-libs-1301331373.cos.ap-hongkong.myqcloud.com/";
    static final File l = new File(GeekTicketMain.instance.getDataFolder(),"libs");
    static final File HikariCP = new File(l, "HikariCP-4.0.3-Ticket-Revise.jar");
    static final File mysqlCj = new File(l, "mysql-8.0.27-Ticket-Revise.jar");

    public static void loadMyLibs() {
        if (isExists()) return;
        getBukkitVersion.put("BukkitVersion", GeekTicketMain.BukkitVersion.substring(0, GeekTicketMain.BukkitVersion.indexOf("-")));
        if (!l.exists()) l.mkdirs();
        if (HikariCP.exists() && HikariCP.length() > 100) {
            loadJarPath(l);
        } else {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §e依赖库不存在，开始自动下载.");
            toDownload();
            loadJarPath(l);
        }
    }
    public static boolean getServer(String s) {
        switch (s) {
            case "1.18.2":
            case "1.18.1":
            case "1.18":
            case "1.17.1":
            case "1.17":
            case "1.16.5":
            case "1.16.4":
            case "1.16.3":
            case "1.16.2":
            case "1.16.1":
            case "1.16":
                return true;
        }
        return false;
    }

    private static void toDownload() {
        try {
            HikariCP.delete();
            mysqlCj.delete();
            if (!getServer(getBukkitVersion.get("BukkitVersion"))) {
                LIbsDownLoad.downloadLibs("mysql-8.0.27-Ticket-Revise.jar");
            }
            LIbsDownLoad.downloadLibs("HikariCP-4.0.3-Ticket-Revise.jar");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载目录中所有的.jar
     * @param path 步骤 1
     */
    private static void loadJarPath(File path) {
        List<File> files = new ArrayList<>();
        ForFile(path, files);
        for (File file : files) {
            LoadLibs.addPath(file, LibsManager.class.getClassLoader());
        }
    }
    private static void ForFile(File file, List<File> files) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                ForFile(tmp, files);
            }
        } else {
            if (file.getAbsolutePath().endsWith(".jar")) {
                files.add(file);
            }
        }
    }
    public static boolean isExists() {
        try {
            Class.forName("com.GeekLib.Ticket.zaxxer.hikari.HikariDataSource");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

}
