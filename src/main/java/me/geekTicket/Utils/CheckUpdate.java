package me.geekTicket.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class CheckUpdate {
    private final static int RESOURCE = 100269;
    public static Map<String, String> getNewVersion = new HashMap<>();

    public CheckUpdate() {
        try {
            final URL url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + RESOURCE);
            URLConnection u = url.openConnection();
            String NewVersion = new BufferedReader(new InputStreamReader(u.getInputStream())).readLine();
            getNewVersion.put("Int", NewVersion.replace("v","").replace(".",""));
            getNewVersion.put("New", NewVersion.replace("v",""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
