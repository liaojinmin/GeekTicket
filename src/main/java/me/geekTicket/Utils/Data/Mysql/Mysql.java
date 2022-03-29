package me.geekTicket.Utils.Data.Mysql;

import com.GeekLib.Ticket.zaxxer.hikari.HikariDataSource;
import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Libs.LibsManager;
import me.geekTicket.Utils.Data.DataBaseHead;
import me.geekTicket.Utils.Task.Task;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class Mysql extends DataBaseHead {

    private static HikariDataSource M;
    public Mysql() {
        super();
    }

    @Override
    public void load() {
        final String MysqlUrl = "jdbc:mysql://" + ConfigManager.HOST + ":" + ConfigManager.PORT + "/" + ConfigManager.DATA_BASE + ConfigManager.PARAMS;
            M = new HikariDataSource();
            M.setJdbcUrl(MysqlUrl);
            M.setUsername(ConfigManager.USER_NAME);
            M.setPassword(ConfigManager.PASS_WORD);
            // 设置数据源驱动路径
           if (!LibsManager.getServer(LibsManager.getBukkitVersion.get("BukkitVersion"))) {
               M.setDriverClassName("com.GeekLib.Ticket.mysql.cj.jdbc.Driver");
           } else {
               M.setDriverClassName("com.mysql.cj.jdbc.Driver");
           }
            M.setMaximumPoolSize(ConfigManager.MAXIMUM_POOL_SIZE);
            M.setMinimumIdle(ConfigManager.MINIMUM_IDLE);
            M.setMaxLifetime(ConfigManager.MAXIMUM_LIFETIME);
            M.setKeepaliveTime(ConfigManager.KEEPALIVE_TIME);
            M.setConnectionTimeout(ConfigManager.CONNECTION_TIMEOUT);
            M.setIdleTimeout(ConfigManager.IDLE_TIMEOUT);
            M.setPoolName("GeekTicket-MYSQL");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return M.getConnection();
    }

    public void stop() {
        if (M != null) {
            M.close();
        }
    }

}
