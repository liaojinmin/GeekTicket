package me.geekTicket.Utils.Data;

import me.geekTicket.TicketAction.TicketDataManager;
import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.Mysql.Mysql;
import me.geekTicket.Utils.Data.Sqlite.Sqlite;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseManager {

    private static DataBaseHead Database;


    public DataBaseManager() {
        start();
    }
    public static Connection getConnection() throws SQLException {
        return Database.getConnection();
    }
    public static void closeData() {
        Database.stop();
    }





    private void start() {
        if (ConfigManager.USE_TYPE.equalsIgnoreCase("mysql")) {
            Database = new Mysql();
            Database.load();
            TicketDataManager.createMysqlTables();
        } else {
            Database = new Sqlite();
            Database.load();
            TicketDataManager.createSqliteTables();
        }
    }


}
