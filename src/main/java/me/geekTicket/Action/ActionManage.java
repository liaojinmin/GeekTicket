package me.geekTicket.Action;

import me.geekTicket.Bukkit.LangMain;
import me.geekTicket.Configuration.ConfigManage;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Obj.TicketObj;
import me.geekTicket.Utils.Data.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 老廖
 * @date : 2022-05-02 21:56
 **/
public class ActionManage implements ActionImpl {

    public static Map<UUID, TicketObj> TicketHashMap = new ConcurrentHashMap<>();
    @Override
    public void onGivePlayerTicket(UUID PlayerUuid, int amount) {
        Player player = (Player) Bukkit.getOfflinePlayer(PlayerUuid);
        int ticket = getPlayerTicket(PlayerUuid) + Math.abs(amount);
        TicketHashMap.put(PlayerUuid, new TicketObj(player.getName(), PlayerUuid, ticket));
        for (String out : LangMain.PLAYER_GIVE_SMG) {
            player.sendMessage(out.replace("[TICKET]", String.valueOf(amount)));
        }
    }

    @Override
    public void onTakePlayerTicket(UUID PlayerUuid, int amount) {
        Player player = (Player) Bukkit.getOfflinePlayer(PlayerUuid);
        int ticket = getPlayerTicket(PlayerUuid);
        if (ticket == 0) {
            GeekTicketMain.say("§7玩家余额不足，已设置为 §f0");
            return;
        }
        int var = 0;
        if (Math.abs(amount) <= ticket) {
            var = ticket - Math.abs(amount);
        }
        TicketHashMap.put(PlayerUuid, new TicketObj(player.getName(), PlayerUuid, var));
        for (String out : LangMain.PLAYER_TAKE_SMG) {
            player.sendMessage(out.replace("[TICKET]", String.valueOf(amount)));
        }

    }

    /**
     * 设置玩家本地缓存的月券数量
     * @param PlayerUuid
     * @param amount
     */
    @Override
    public void setPlayerTicket(UUID PlayerUuid, int amount) {
        Player player = (Player) Bukkit.getOfflinePlayer(PlayerUuid);
        TicketHashMap.put(PlayerUuid, new TicketObj(player.getName(), PlayerUuid, amount));

    }

    /**
     * 获取玩家本地缓存的 月券数据
     * @param uuid
     * @return
     */
    @Override
    public int getPlayerTicket(UUID uuid) {
        String name = Bukkit.getOfflinePlayer(uuid).getName();
        assert name != null;
        if (TicketHashMap.isEmpty()) {
            TicketHashMap.put(uuid, new TicketObj(name, uuid, 0));
            return 0;
        }
        TicketObj data = TicketHashMap.get(uuid);
        if (data == null) {
            TicketHashMap.put(uuid, new TicketObj(name, uuid, 0));
            return 0;
        }
        if (data.getPLAYER_NAME().equalsIgnoreCase(name)) {
            return data.getTICKET();
        } else {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §c数据对比出错，当前玩家UUID不一致 §f- §e取消操作！");
        }
        return 0;
    }
    public boolean isTicket(Player player, int amount) {
        if (TicketHashMap.isEmpty()) {
            return false;
        }
        TicketObj data = TicketHashMap.get(player.getUniqueId());
        if (data == null) return false;
        return data.getPLAYER_NAME().equalsIgnoreCase(player.getName()) && data.getTICKET() >= amount;
    }



    /*
     *   以下是有数据库操作的方法
     */
    /**
     * 从数据库获取这个玩家的月券数据
     * @param uuid 玩家UUID
     */
    @Override
    public int getPlayerData(UUID uuid) {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + ConfigManage.MYSQL_DATA_NAME+ " WHERE uuid=?;")) {
                statement.setString(1, String.valueOf(uuid));
                ResultSet res = statement.executeQuery();
                if (!res.isBeforeFirst()) {
                    AddDefaultData(Bukkit.getOfflinePlayer(uuid).getName(), uuid);
                    return 0;
                }
                int roll = 0;
                while (res.next()) {
                    String name = res.getString("name");
                    roll = res.getInt("roll");
                    TicketHashMap.put(uuid, new TicketObj(name, uuid, roll));
                }
                return roll;
            }
        } catch (SQLException e) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §C更新玩家数据失败-错误定位-getPlayerData");
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
            GeekTicketMain.say("数据库错误-setPlayerData");
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
        TicketHashMap.put(uuid, new TicketObj(name, uuid, 0));
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
