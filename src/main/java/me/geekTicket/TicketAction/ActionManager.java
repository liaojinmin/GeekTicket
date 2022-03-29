package me.geekTicket.TicketAction;

import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.Utils.Data.DataBaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class ActionManager {


    /**
     * 给予玩家月券
     * @param PlayerUuid 玩家UUID
     * @param amount 需要给予的数量
     */
    public static void giveTicket(UUID PlayerUuid, int amount) {
        String PlayerName = getPLayerName(PlayerUuid);
        int ticket = getPlayerTicket(PlayerName, PlayerUuid) + Math.abs(amount);

        setUpdate(PlayerName, PlayerUuid, ticket);
        Player player = getPLayer(PlayerUuid);
        for (String out : ConfigManager.PLAYER_GIVE_SMG) {
            player.sendMessage(out.replace("[TICKET]", String.valueOf(amount)));
        }
    }

    /**
     * 扣除玩家月券
     * @param PlayerUuid UUID
     * @param amount 数量
     */
    public static void takeTicket(UUID PlayerUuid, int amount) {
        String PlayerName = getPLayerName(PlayerUuid);
        int all = 0;
        int ticket = getPlayerTicket(PlayerName, PlayerUuid);
        if (ticket == 0) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §7玩家余额不足，已设置为 §f0");
            return;
        }
        if (Math.abs(amount) <= ticket) {
            all = ticket - Math.abs(amount);
        }
        setUpdate(PlayerName, PlayerUuid, all);
        Player player = getPLayer(PlayerUuid);
        for (String out : ConfigManager.PLAYER_TAKE_SMG) {
            player.sendMessage(out.replace("[TICKET]", String.valueOf(amount)));
        }

    }


    /**
     * 从缓存中获取玩家持有的月卷数量
     * @param name 玩家名称
     * @param uuid uuid名称
     * @return 返回持有数，无则null
     */
    public static int getPlayerTicket(String name, UUID uuid) {
        if (TicketDataManager.getTicketMap.isEmpty()) {
            AddDefaultData(name, uuid);
            return 0;
        }
        TicketObj data = TicketDataManager.getTicketMap.get(uuid);
        if (data == null) {
            AddDefaultData(name, uuid);
            return 0;
        }
        if (data.PLAYER_NAME.equalsIgnoreCase(name)) {
            return data.TICKET;
        } else {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §c数据对比出错，当前玩家UUID不一致 §f- §e取消操作！");
        }
        return 0;
    }

    /**
     *检查玩家是否拥有指定数量的月券
     *
     * @param name 玩家名称
     * @param uuid uuid
     * @param amount 需要检查的数量
     * @return 返回布尔值
     */
    public static boolean isTicket(String name, String uuid, int amount) {
        if (TicketDataManager.getTicketMap.isEmpty()) {
            return false;
        }
        TicketObj data = TicketDataManager.getTicketMap.get(uuid);
        if (data == null) return false;
        return data.PLAYER_NAME.equalsIgnoreCase(name) && data.TICKET >= amount;
    }



    private static void setUpdate(String PlayerName, UUID PlayerUuid, int amount) {
        try (Connection connection = DataBaseManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `roll_data` SET `roll`=? WHERE `uuid`=?;")) {
                statement.setInt(1, amount);
                statement.setString(2, String.valueOf(PlayerUuid));
                statement.executeUpdate();
            }
            TicketDataManager.getTicketMap.put(PlayerUuid, new TicketObj(PlayerName, PlayerUuid, amount));

        } catch (SQLException e) {
            GeekTicketMain.say("数据库错误-setUpdate");
            e.printStackTrace();
        }
    }


    private static void AddDefaultData(String name, UUID uuid) {
        TicketDataManager.getTicketMap.put(uuid, new TicketObj(name, uuid, 0));
        try (Connection connection = DataBaseManager.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO roll_data(`name`,`uuid`) VALUES(?,?);")) {
                statement.setString(1, name);
                statement.setString(2, String.valueOf(uuid));
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Player getPLayer(UUID playerUuid) {
        return (Player) Bukkit.getOfflinePlayer(playerUuid);
    }
    private static String getPLayerName(UUID playerUuid) {
        return Bukkit.getPlayer(playerUuid).getName();
    }


}
