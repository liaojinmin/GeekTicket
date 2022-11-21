package me.geekTicket.Utils.AutoMatic;

import me.geekTicket.Action.ActionManage;
import me.geekTicket.Configuration.ConfigManage;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Obj.TicketObj;
import me.geekTicket.Obj.TopObj;
import me.geekTicket.Utils.Data.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 老廖
 * @date : 2022-05-1 00:54
 **/

public class Task {
    public static Map<Integer, TopObj> LocalTop = new ConcurrentHashMap<>();
    public static Map<Integer, TopObj> GlobalTop = new ConcurrentHashMap<>();

    public void onMapHashUp() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = DataBaseManager.getConnection()) {
                    try (Statement statement = connection.createStatement()) {
                        ResultSet res = statement.executeQuery("SELECT * FROM "+ ConfigManage.MYSQL_DATA_NAME + " WHERE id");
                        if (!res.isBeforeFirst()) {
                            return;
                        }
                        ActionManage.TicketHashMap.clear();
                        while (res.next()) {
                            String name = res.getString("name");
                            UUID uuid = UUID.fromString(res.getString("uuid"));
                            int roll = res.getInt("roll");
                            TicketObj a = new TicketObj(name, uuid, roll);
                            ActionManage.TicketHashMap.put(uuid, a);
                        }
                    }
                } catch (SQLException e) {
                    GeekTicketMain.say("§8[§3§lGeekTicket§8] §C数据更新任务失败-MapHashUp");
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(GeekTicketMain.INSTANCE.getInstance());
    }

    public void onSave() {
        new BukkitRunnable() {
            @Override
            public void run() {
                onActiveUpdate();
            }
        }.runTaskAsynchronously(GeekTicketMain.INSTANCE.getInstance());
    }
    public void onActiveUpdate() {
        GeekTicketMain.say("正在保存数据...");
        try (Connection connection = DataBaseManager.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("UPDATE "+ ConfigManage.MYSQL_DATA_NAME +" SET `roll`=? WHERE `uuid`=?;")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    statement.setInt(1, GeekTicketMain.getAction().getPlayerTicket(player.getUniqueId()));
                    statement.setString(2, player.getUniqueId().toString());
                    statement.executeUpdate();
                }
            }
        } catch (Exception e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §c主动更新失败-ActiveUpdate");
            e.printStackTrace();
        }
    }
    public void onClearAllTicket() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (new Date().getDate() == ConfigManage.AUTO_CLEAR_DAY && ConfigManage.AUTO_CLEAR) {
                    try (Connection connection = DataBaseManager.getConnection()) {
                        try (Statement statement = connection.createStatement()) {
                            statement.execute("truncate table " + ConfigManage.MYSQL_DATA_NAME + ";");
                        }
                        GeekTicketMain.say("数据库已清空");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        GeekTicketMain.say("数据清空时发生错误-TimeRun");
                    }
                }
            }
        }.runTaskTimerAsynchronously(GeekTicketMain.INSTANCE.getInstance(),60 * 20, 60 * 20);
    }

    public void getLocalTop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Map.Entry<UUID,TicketObj>> list = new ArrayList<>(ActionManage.TicketHashMap.entrySet());
                list.sort((o1, o2) -> Integer.compare(o2.getValue().getTICKET(), o1.getValue().getTICKET()));
                try {
                    for (int i = 0; i <= list.size(); i++) {
                        if (list.get(i) != null) {
                            LocalTop.put(i + 1, new TopObj(list.get(i).getValue().getPLAYER_NAME(), list.get(i).getValue().getTICKET()));
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }.runTaskTimerAsynchronously(GeekTicketMain.INSTANCE.getInstance(),60 * 20, 60 * 20);
    }
    public void getGlobalTop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = DataBaseManager.getConnection()){
                    try (Statement s = connection.createStatement()){
                        ResultSet res = s.executeQuery("SELECT `name`, `roll` FROM " + ConfigManage.MYSQL_DATA_NAME + " ORDER BY roll DESC limit 10");
                        int index = 1;
                        while (res.next()) {
                            String name = res.getString("name");
                            int roil = res.getInt("roll");
                            GlobalTop.put(index, new TopObj(name, roil));
                            index++;
                        }
                    }
                } catch (Exception e) {
                    GeekTicketMain.say("§8[§3§lGeekTicket§8] §c数据更新任务失败，getLeaderboard");
                    e.printStackTrace();
                }
            }
        }.runTaskTimerAsynchronously(GeekTicketMain.INSTANCE.getInstance(),60 * 20, 60 * 20);
    }
}
