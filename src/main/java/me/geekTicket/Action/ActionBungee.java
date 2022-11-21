package me.geekTicket.Action;

import me.geekTicket.Configuration.ConfigManage;
import me.geekTicket.GeekTicketBC;
import me.geekTicket.Utils.Data.DataBaseManager;
import net.md_5.bungee.api.ProxyServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author : 老廖
 * @date : 2022-05-04 11:17
 **/
public class ActionBungee implements ActionImpl {


    @Override
    public void onGivePlayerTicket(UUID PlayerUuid, int amount) {
    }

    @Override
    public void onTakePlayerTicket(UUID PlayerUuid, int amount) {
    }

    @Override
    public void setPlayerTicket(UUID PlayerUuid, int amount) {

    }

    @Override
    public int getPlayerTicket(UUID PlayerUuid) {
        return 0;
    }

    /**
     * 从数据库获取这个玩家的月券数据
     *
     * @param uuid 玩家UUID
     */
    @Override
    public int getPlayerData(UUID uuid) {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + ConfigManage.MYSQL_DATA_NAME + " WHERE uuid=?;")) {
                statement.setString(1, String.valueOf(uuid));
                ResultSet res = statement.executeQuery();
                if (!res.isBeforeFirst()) {
                    AddDefaultData(ProxyServer.getInstance().getPlayer(uuid).getName(), uuid);
                    return 0;
                }
                int out = 0;
                while (res.next()) {
                    out = res.getInt("roll");
                }
                return out;
            }
        } catch (SQLException e) {
            GeekTicketBC.say("§c更新玩家数据失败-错误定位-getPlayerData");
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 往数据库更新玩家 月券数据
     * @param PlayerUuid 玩家UUID
     * @param amount 月券数量
     */
    @Override
    public void setPlayerData(UUID PlayerUuid, int amount) {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE "+ ConfigManage.MYSQL_DATA_NAME +" SET `roll`=? WHERE `uuid`=?;")) {
                statement.setInt(1, amount);
                statement.setString(2, String.valueOf(PlayerUuid));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            GeekTicketBC.say("数据库错误-setPlayerData");
            e.printStackTrace();
        }
    }

    /**
     * 往数据库添加玩家默认月券数据
     * @param name 玩家名称
     * @param uuid 玩家UUID
     */
    @Override
    public void AddDefaultData(String name, UUID uuid) {
        try (Connection connection = DataBaseManager.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO " + ConfigManage.MYSQL_DATA_NAME + "(`name`,`uuid`) VALUES(?,?);")) {
                statement.setString(1, name);
                statement.setString(2, String.valueOf(uuid));
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
