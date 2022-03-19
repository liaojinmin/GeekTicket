package me.geekTicket.Utils.Data.Mysql;

import com.GeekLib.Ticket.zaxxer.hikari.HikariDataSource;
import me.geekTicket.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Libs.LibsManager;
import me.geekTicket.Utils.Data.DataHead;
import me.geekTicket.Utils.Task.Task;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class Mysql extends DataHead {

    private static final String host = ConfigManager.getConfig().getString("data_storage.mysql.host");
    private static final int port = ConfigManager.getConfig().getInt("data_storage.mysql.port");
    private static final String database = ConfigManager.getConfig().getString("data_storage.mysql.database");
    private static final String username = ConfigManager.getConfig().getString("data_storage.mysql.username");
    private static final String password = ConfigManager.getConfig().getString("data_storage.mysql.password");
    private static final String params = ConfigManager.getConfig().getString("data_storage.mysql.params");
    private static final String SQL_CREATE_1 = "CREATE TABLE IF NOT EXISTS `roll_data` ( `id` INT(80) NOT NULL AUTO_INCREMENT , `name` VARCHAR(255) NOT NULL UNIQUE , `uuid` CHAR(36) NOT NULL UNIQUE , `roll` INT(80) NOT NULL DEFAULT '0', `crafttime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , `updatatime` TIMESTAMP on update CURRENT_TIMESTAMP NULL , PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    private static HikariDataSource M;
    public Mysql() {
        super();
    }

    @Override
    public void load() {
        final String MysqlUrl = "jdbc:mysql://" + host + ":" + port + "/" + database + params;
            M = new HikariDataSource();
            M.setJdbcUrl(MysqlUrl);
            M.setUsername(username);
            M.setPassword(password);
            // 设置数据源驱动路径
           if (!LibsManager.getServer(LibsManager.getBukkitVersion.get("BukkitVersion"))) {
               M.setDriverClassName("com.GeekLib.Ticket.mysql.cj.jdbc.Driver");
           } else {
               M.setDriverClassName("com.mysql.cj.jdbc.Driver");
           }
            M.setMaximumPoolSize(ConfigManager.maximum_pool_size);
            M.setMinimumIdle(ConfigManager.minimum_idle);
            M.setMaxLifetime(ConfigManager.maximum_lifetime);
            M.setKeepaliveTime(ConfigManager.keepalive_time);
            M.setConnectionTimeout(ConfigManager.connection_timeout);
            M.setIdleTimeout(ConfigManager.idle_timeout);
            M.setPoolName("GeekTicket-MYSQL");
    }

    @Override
    public void createTables() {
        try (Connection connection = M.getConnection()) {
            try (Statement statement = connection.createStatement()){
                statement.execute(SQL_CREATE_1);
                Task.getMapHashUp();
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §A创建数据库表时出错，你使用了正确的版本吗?");
            Bukkit.getPluginManager().disablePlugin(GeekTicketMain.instance);
        }
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
