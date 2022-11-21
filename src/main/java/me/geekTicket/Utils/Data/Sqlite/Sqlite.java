package me.geekTicket.Utils.Data.Sqlite;

import com.zaxxer.hikari.HikariDataSource;
import me.geekTicket.Configuration.ConfigManage;
import me.geekTicket.Utils.Data.DataBaseHead;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;


public final class Sqlite extends DataBaseHead {

    private HikariDataSource SQLITE;

    public Sqlite() {
        super();
    }

    @Override
    public void load() {
        final String SqliteUrl = "jdbc:sqlite:"+ System.getProperty("user.dir") +  File.separator +"plugins"+ File.separator + "GeekTicket"+ File.separator + "/Geek-Roll.db";
        SQLITE = new HikariDataSource();
        SQLITE.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        SQLITE.addDataSourceProperty("url", SqliteUrl);
        //附件参数
        SQLITE.setMaximumPoolSize(ConfigManage.MAXIMUM_POOL_SIZE);
        SQLITE.setMinimumIdle(ConfigManage.MINIMUM_IDLE);
        SQLITE.setMaxLifetime(ConfigManage.MAXIMUM_LIFETIME);
        SQLITE.setKeepaliveTime(ConfigManage.KEEPALIVE_TIME);
        SQLITE.setConnectionTimeout(ConfigManage.CONNECTION_TIMEOUT);
        SQLITE.setPoolName("GeekTicket-SQLITE");
    }
    @Override
    public Connection getConnection() throws SQLException {
        return SQLITE.getConnection();
    }

    @Override
    public void stop() {
        if (SQLITE != null) {
            SQLITE.close();
        }
    }
}
