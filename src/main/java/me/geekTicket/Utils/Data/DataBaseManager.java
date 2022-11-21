package me.geekTicket.Utils.Data;
import me.geekTicket.Configuration.ConfigManage;
import me.geekTicket.Utils.Data.Mysql.Mysql;
import me.geekTicket.Utils.Data.Sqlite.Sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBaseManager {

    private static DataBaseHead Database;

    public DataBaseManager() {
        if (Database == null) {
            start();
        }
    }
    public static Connection getConnection() throws SQLException {
        return Database.getConnection();
    }
    public static void closeData() {
        Database.stop();
    }

    private void start() {
        if (ConfigManage.DATA_TYPE.equalsIgnoreCase("mysql")) {
            Database = new Mysql();
            Database.load();
            createMysqlTables();
        } else {
            Database = new Sqlite();
            Database.load();
            createSqliteTables();
        }
    }


    private void createSqliteTables() {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (Statement statement = connection.createStatement()){
                statement.execute("PRAGMA foreign_keys = ON;");
                statement.execute("PRAGMA encoding = 'UTF-8';");
                statement.execute("CREATE TABLE IF NOT EXISTS `roll_data` (" +
                        " `id` integer PRIMARY KEY ," +
                        " `name` VARCHAR(255) NOT NULL UNIQUE ," +
                        " `uuid` CHAR(36) NOT NULL UNIQUE ," +
                        " `roll` INT(80) NOT NULL DEFAULT '0');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createMysqlTables() {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (Statement statement = connection.createStatement()){
                statement.execute("CREATE TABLE IF NOT EXISTS `roll_data` (" +
                        " `id` INT(80) NOT NULL AUTO_INCREMENT ," +
                        " `name` VARCHAR(255) NOT NULL UNIQUE ," +
                        " `uuid` CHAR(36) NOT NULL UNIQUE ," +
                        " `roll` INT(80) NOT NULL DEFAULT '0'," +
                        " PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
