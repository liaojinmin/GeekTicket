package me.geekTicket.Utils.Data.Sqlite;

import com.GeekLib.Ticket.zaxxer.hikari.HikariDataSource;
import me.geekTicket.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.DataHead;
import me.geekTicket.Utils.Task.Task;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class Sqlite extends DataHead {


    private static final String SETUP_A = "PRAGMA foreign_keys = ON;";
    private static final String SETUP_B = "PRAGMA encoding = 'UTF-8';";
    private static final String SQL_CREATE_1 = "CREATE TABLE IF NOT EXISTS `roll_data` ( `id` integer PRIMARY KEY , `name` VARCHAR(255) NOT NULL UNIQUE , `uuid` CHAR(36) NOT NULL UNIQUE , `roll` INT(80) NOT NULL DEFAULT '0');";

    private HikariDataSource S;

    public Sqlite() {
        super();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return S.getConnection();
    }

    @Override
    public void load() {
        final String SqliteUrl = "jdbc:sqlite:"+ ConfigManager.getDataFolder()+"/Geek-Roll.db";
        S = new HikariDataSource();
        S.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        S.addDataSourceProperty("url", SqliteUrl);
        //附件参数
        S.setMaximumPoolSize(ConfigManager.maximum_pool_size);
        S.setMinimumIdle(ConfigManager.minimum_idle);
        S.setMaxLifetime(ConfigManager.maximum_lifetime);
        S.setKeepaliveTime(ConfigManager.keepalive_time);
        S.setConnectionTimeout(ConfigManager.connection_timeout);
        S.setIdleTimeout(ConfigManager.idle_timeout);
        S.setPoolName("GeekTicket-SQLITE");
    }

    @Override
    public void createTables() {
        try (Connection connection = S.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(SETUP_A);
                statement.execute(SETUP_B);
                statement.execute(SQL_CREATE_1);
                Task.getMapHashUp();
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §A创建数据库表时出错，你使用了正确的版本吗?-create()");
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §ASQLITE err err?-create()");
            Bukkit.getPluginManager().disablePlugin(GeekTicketMain.instance);
        }
    }

    @Override
    public void stop() {
        if (S != null) {
            S.close();
        }
    }
}
