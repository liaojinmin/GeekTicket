package me.geekTicket.Utils.Data;

import me.geekTicket.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.Mysql.Mysql;
import me.geekTicket.Utils.Data.Sqlite.Sqlite;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {

    public static Map<String, PlayerDataConstructor> getMapData = new HashMap<>();
    public static Map<Integer, LeaderboardConstructor> roil_top = new ConcurrentHashMap<>();

    private static DataHead Database;


    public static Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    public DataManager() {
        start();
    }
    public static void closeData() {
        Database.stop();
    }


    /**
     * 从数据库获取玩家数据
     * 并将数据写入Map缓存中
     * 如果数据库也没数据则什么也不做
     *
     * @param uuid 玩家uuid
     */
    public static void UpPlayerData(String uuid) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `roll_data` WHERE uuid=?;")) {
                statement.setString(1, uuid);
                ResultSet res = statement.executeQuery();
                if (!res.isBeforeFirst()) return;
                while (res.next()) {
                    String name = res.getString("name");
                    int roll = res.getInt("roll");
                    PlayerDataConstructor a = new PlayerDataConstructor(name, uuid, roll);
                    DataManager.getMapData.put(uuid, a);
                }
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §C更新玩家数据失败-错误定位-UpPlayerData");
            e.printStackTrace();
        }
    }

    private void start() {
        if (ConfigManager.getConfig().getString("data_storage.use_type").equalsIgnoreCase("mysql")) {
            Database = new Mysql();
        } else {
            Database = new Sqlite();
        }
        Database.load();
        Database.createTables();
    }


}
