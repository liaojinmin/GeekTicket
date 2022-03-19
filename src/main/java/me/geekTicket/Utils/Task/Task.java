package me.geekTicket.Utils.Task;

import me.geekTicket.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;
import me.geekTicket.Utils.Data.LeaderboardConstructor;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class Task {


    public static void getMapHashUp() {
        new BukkitRunnable() {

            @Override
            public void run() {
                try (Connection connection = DataManager.getConnection()) {
                    try (Statement statement = connection.createStatement()) {
                        ResultSet res = statement.executeQuery("SELECT * FROM `roll_data` WHERE id");
                        if (!res.isBeforeFirst()) {
                            return;
                        }
                        DataManager.getMapData.clear();
                        while (res.next()) {
                            String name = res.getString("name");
                            String uuid = res.getString("uuid");
                            int roll = res.getInt("roll");
                            PlayerDataConstructor a = new PlayerDataConstructor(name, uuid, roll);
                            DataManager.getMapData.put(uuid, a);
                        }
                    }
                } catch (SQLException e) {
                    GeekTicketMain.say("§8[§3§lGeekTicket§8] §C数据更新任务失败-MapHashUp");
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(GeekTicketMain.instance);
    }

    public static void getLeaderboard(){
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = DataManager.getConnection()){
                    try (PreparedStatement statement = connection.prepareStatement("SELECT `name`, `roll` FROM `roll_data` ORDER BY roll DESC limit 10")) {
                        ResultSet res = statement.executeQuery();
                        if (res == null) {
                            GeekTicketMain.say("未知错误 - 排行榜异步错误");
                            return;
                        }
                        DataManager.roil_top.clear();
                        int index = 1;
                        while (res.next()) {
                            String name = res.getString("name");
                            int roil = res.getInt("roll");
                            LeaderboardConstructor a = new LeaderboardConstructor(name, roil);
                            DataManager.roil_top.put(index, a);
                            index++;
                        }
                    }
                    if (ConfigManager.getConfig().getBoolean("auto_clear")) {
                        ClearAction.Run(ConfigManager.getConfig().getString("auto_clear_day"));
                    }
                } catch (Exception e) {
                    GeekTicketMain.say("§8[§3§lGeekTicket§8] §C排行榜任务失败");
                    e.printStackTrace();
                }
            }
        }.runTaskTimerAsynchronously(GeekTicketMain.instance,60 * 20, 60 * 20);
    }
}
