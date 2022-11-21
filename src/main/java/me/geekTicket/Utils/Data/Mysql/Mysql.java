package me.geekTicket.Utils.Data.Mysql;

import com.zaxxer.hikari.HikariDataSource;
import me.geekTicket.Configuration.ConfigManage;
import me.geekTicket.Utils.Data.DataBaseHead;

import java.sql.Connection;
import java.sql.SQLException;


public final class Mysql extends DataBaseHead {

    private HikariDataSource MYSQL;
    public Mysql() {
        super();
    }

    @Override
    public void load() {
        final String MysqlUrl = "jdbc:mysql://" + ConfigManage.MYSQL_HOST + ":" + ConfigManage.MYSQL_PORT + "/" + ConfigManage.MYSQL_DATABASE + ConfigManage.MYSQL_PARAMS;
            MYSQL = new HikariDataSource();
            MYSQL.setJdbcUrl(MysqlUrl);
            MYSQL.setUsername(ConfigManage.MYSQL_USERNAME);
            MYSQL.setPassword(ConfigManage.MYSQL_PASSWORD);
            try {
                MYSQL.setDriverClassName("com.mysql.cj.jdbc.Driver");
            } catch (NoClassDefFoundError e) {
                MYSQL.setDriverClassName("com.mysql.jdbc.Driver");
            }
           MYSQL.setMaximumPoolSize(ConfigManage.MAXIMUM_POOL_SIZE);
           MYSQL.setMinimumIdle(ConfigManage.MINIMUM_IDLE);
           MYSQL.setMaxLifetime(ConfigManage.MAXIMUM_LIFETIME);
           MYSQL.setKeepaliveTime(ConfigManage.KEEPALIVE_TIME);
           MYSQL.setConnectionTimeout(ConfigManage.CONNECTION_TIMEOUT);
           MYSQL.setPoolName("GeekTicket-MYSQL");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return MYSQL.getConnection();
    }

    public void stop() {
        if (MYSQL != null) {
            MYSQL.close();
        }
    }

}
