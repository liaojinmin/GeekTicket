package me.geekTicket.TicketAction;

import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.DataBaseManager;
import me.geekTicket.Utils.Task.Task;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class TicketDataManager {

    public static Map<UUID, TicketObj> getTicketMap = new HashMap<>();
    public static Map<Integer, LeaderboardObj> getTicketTopMap = new ConcurrentHashMap<>();

    /**
     * 从数据库获取玩家数据
     * 并将数据写入Map缓存中
     * 如果数据库也没数据则什么也不做
     *
     * @param uuid 玩家uuid
     */
    public static void getPlayerData(UUID uuid) {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `roll_data` WHERE uuid=?;")) {
                statement.setString(1, String.valueOf(uuid));
                ResultSet res = statement.executeQuery();
                if (!res.isBeforeFirst()) return;
                while (res.next()) {
                    String name = res.getString("name");
                    int roll = res.getInt("roll");
                    getTicketMap.put(uuid, new TicketObj(name, uuid, roll));
                }
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §C更新玩家数据失败-错误定位-getPlayerData");
            e.printStackTrace();
        }
    }

    public static void createSqliteTables() {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (Statement statement = connection.createStatement()){
                statement.execute("PRAGMA foreign_keys = ON;");
                statement.execute("PRAGMA encoding = 'UTF-8';");
                statement.execute("CREATE TABLE IF NOT EXISTS `roll_data` (" +
                        " `id` integer PRIMARY KEY ," +
                        " `name` VARCHAR(255) NOT NULL UNIQUE ," +
                        " `uuid` CHAR(36) NOT NULL UNIQUE ," +
                        " `roll` INT(80) NOT NULL DEFAULT '0');");
                Task.getMapHashUp();
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §A创建数据库表时出错， 你使用了正确的版本吗?");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(GeekTicketMain.instance);
        }
    }

    public static void createMysqlTables() {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (Statement statement = connection.createStatement()){
                statement.execute("CREATE TABLE IF NOT EXISTS `roll_data` (" +
                        " `id` INT(80) NOT NULL AUTO_INCREMENT , `name` VARCHAR(255) NOT NULL UNIQUE , `uuid` CHAR(36) NOT NULL UNIQUE , `roll` INT(80) NOT NULL DEFAULT '0', `crafttime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , `updatatime` TIMESTAMP on update CURRENT_TIMESTAMP NULL , PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8;");
                Task.getMapHashUp();
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §A创建数据库表时出错，你使用了正确的版本吗?");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(GeekTicketMain.instance);
        }
    }
}
