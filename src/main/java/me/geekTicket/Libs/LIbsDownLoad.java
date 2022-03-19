package me.geekTicket.Libs;

import me.geekTicket.GeekTicketMain;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class LIbsDownLoad extends LibsManager{

    public static void downloadLibs(String str) throws Throwable{
        String lib = l.getCanonicalPath();
        FileOutputStream savePath = new FileOutputStream(lib + File.separator +str);
        URL url = new URL(httpUrl+str);
        URLConnection conn = url.openConnection();
        conn.setReadTimeout(300000);
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        GeekTicketMain.say("§8[§3§lGeekTicket§8] §8下载库: §7" + str);
        try {
            InputStream i = conn.getInputStream();
            byte[] b = readInputStream(i);
            savePath.write(b);
        } catch (FileNotFoundException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §c扩展库: §7" + str + "§c下载失败，请检查你的网络链接");
           // e.printStackTrace();
        }
    }
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
