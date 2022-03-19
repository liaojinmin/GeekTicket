package me.geekTicket.RollAction;

import me.geekTicket.GeekTicketMain;
import me.geekTicket.Language;
import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Action extends ActionHead {

    public boolean ActionState;

    @Override
    public void give(String name, int a) {
        Player p = (Player) Bukkit.getOfflinePlayer(name);
        String uuid = String.valueOf(p.getUniqueId());
        int all = getPlayerRoll(name, uuid) + Math.abs(a);
        //debug
        //GeekTicketMain.say(  all+ "-" + name + "-" + uuid);
        postSql(uuid, all);
        if (!ActionState) return;
        postMapData(name,uuid,all);
        for (String out : Language.OnGive) {
            p.sendMessage(out.replace("[TICKET]", String.valueOf(a)));
        }
    }


    @Override
    public void take(String name, int a) {
        Player p = (Player) Bukkit.getOfflinePlayer(name);
        String uuid = String.valueOf(p.getUniqueId());
        int all = 0;
        if (getPlayerRoll(name, uuid) == 0) {
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §7玩家余额不足，已设置为 §f0");
            return;
        }
        if (Math.abs(a) <= getPlayerRoll(name, uuid)) {
            all = getPlayerRoll(name, uuid) - Math.abs(a);
        }
        postSql(uuid, all);
        if (!ActionState) return;
        postMapData(name,uuid,all);
        for (String out : Language.OnTake) {
            p.sendMessage(out.replace("[TICKET]", String.valueOf(a)));
        }
    }

    private void postSql(String uuid, int all) {
        try (Connection connection = DataManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `roll_data` SET `roll`=? WHERE `uuid`=?;")) {
                statement.setInt(1, all);
                statement.setString(2, uuid);
                statement.executeUpdate();
            }
            ActionState = true;
        } catch (SQLException e) {
            GeekTicketMain.say("数据库错误-post");
            e.printStackTrace();
            ActionState = false;
        }
    }

    private void postMapData(String name, String uuid, int all) {
        PlayerDataConstructor d = new PlayerDataConstructor(name, uuid, all);
        DataManager.getMapData.put(uuid, d);
        ActionState = true;
    }


}
