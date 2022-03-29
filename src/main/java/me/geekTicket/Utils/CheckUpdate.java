package me.geekTicket.Utils;

import me.geekTicket.GeekTicketMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public final class CheckUpdate {
    private final static int RESOURCE = 100269;
    public static Map<String, String> getNewVersion = new HashMap<>();

    public CheckUpdate() {
        try {
            final URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + RESOURCE);
            URLConnection u = url.openConnection();
            String NewVersion = new BufferedReader(new InputStreamReader(u.getInputStream())).readLine();
            getNewVersion.put("Int", NewVersion.replace("v","").replace(".",""));
            getNewVersion.put("New", NewVersion.replace("v",""));
            check();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void check() {
        if (Integer.parseInt(getNewVersion.get("Int")) > Integer.parseInt(GeekTicketMain.Version.replace(".", ""))) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §8发现新版本待更新, 当前版本:§f " + GeekTicketMain.Version + " §8最新版本:§f " + CheckUpdate.getNewVersion.get("New"));
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §8Found a new version to be updated, the current version:§f " + GeekTicketMain.Version + " §8New:§f " + CheckUpdate.getNewVersion.get("New"));
        } else {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §8你使用的是最新版本§f " + GeekTicketMain.Version);
        }
    }
}
