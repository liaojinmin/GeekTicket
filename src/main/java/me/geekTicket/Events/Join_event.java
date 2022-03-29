package me.geekTicket.Events;

import me.geekTicket.TicketAction.ActionManager;
import me.geekTicket.TicketAction.TicketDataManager;
import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.CheckUpdate;
import me.geekTicket.TicketAction.TicketObj;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Join_event implements Listener {


    @EventHandler
    public void onJoin_UpMeta(PlayerJoinEvent event) {
        Player eventPlayer = event.getPlayer();
        UUID uuid = eventPlayer.getUniqueId();
        new BukkitRunnable() {
            public void run(){
                if (eventPlayer.isOp()) {
                    if (Integer.parseInt(CheckUpdate.getNewVersion.get("Int")) > Integer.parseInt(GeekTicketMain.Version.replace(".",""))) {
                        eventPlayer.sendMessage("§8[§3§lGeekTicket§8] §8发现新版本待更新, 当前版本:§f " + GeekTicketMain.Version + " §8最新版本:§f " + CheckUpdate.getNewVersion.get("New"));
                        eventPlayer.sendMessage("§8[§3§lGeekTicket§8] §8Found a new version to be updated, the current version:§f " + GeekTicketMain.Version + " §8New:§f " + CheckUpdate.getNewVersion.get("New"));
                    }
                }
                TicketObj data = TicketDataManager.getTicketMap.get(uuid);
                if (data == null) {
                    TicketDataManager.getPlayerData(uuid);
                    return;
                }
                if (data.TICKET > 0 && ConfigManager.AUTO_CLEAR) {
                    int day = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("dd")));
                    eventPlayer.sendMessage(ConfigManager.PLUGIN_NAME + ConfigManager.JOIN_SMG + " §7今天是: " + day + " §7号" );
                }
            }
        }.runTaskAsynchronously(GeekTicketMain.instance);
    }
}
