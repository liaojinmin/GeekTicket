package me.geekTicket.Utils.Data.Sqlite;

import com.GeekLib.Ticket.zaxxer.hikari.HikariDataSource;
import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.DataBaseHead;


import java.sql.Connection;
import java.sql.SQLException;

public final class Sqlite extends DataBaseHead {

    private HikariDataSource S;

    public Sqlite() {
        super();
    }

    @Override
    public void load() {
        final String SqliteUrl = "jdbc:sqlite:"+ GeekTicketMain.instance.getDataFolder()+"/Geek-Roll.db";
        S = new HikariDataSource();
        S.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        S.addDataSourceProperty("url", SqliteUrl);
        //附件参数
        S.setMaximumPoolSize(ConfigManager.MAXIMUM_POOL_SIZE);
        S.setMinimumIdle(ConfigManager.MINIMUM_IDLE);
        S.setMaxLifetime(ConfigManager.MAXIMUM_LIFETIME);
        S.setKeepaliveTime(ConfigManager.KEEPALIVE_TIME);
        S.setConnectionTimeout(ConfigManager.CONNECTION_TIMEOUT);
        S.setIdleTimeout(ConfigManager.IDLE_TIMEOUT);
        S.setPoolName("GeekTicket-SQLITE");
    }
    @Override
    public Connection getConnection() throws SQLException {
        return S.getConnection();
    }

    @Override
    public void stop() {
        if (S != null) {
            S.close();
        }
    }
}
