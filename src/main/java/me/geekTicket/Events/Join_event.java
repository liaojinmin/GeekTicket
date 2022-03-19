package me.geekTicket.Events;

import me.geekTicket.ConfigManager;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Language;
import me.geekTicket.Utils.CheckUpdate;
import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Join_event implements Listener {


    @EventHandler
    public void onJoin_UpMeta(PlayerJoinEvent event) {
        Player eventPlayer = event.getPlayer();
        String uuid = String.valueOf(eventPlayer.getUniqueId());
        new BukkitRunnable() {
            public void run(){
                if (eventPlayer.isOp()) {
                    if (Integer.parseInt(CheckUpdate.getNewVersion.get("Int")) > Integer.parseInt(GeekTicketMain.Version.replace(".",""))) {
                        eventPlayer.sendMessage("§8[§3§lGeekTicket§8] §8发现新版本待更新, 当前版本:§f " + GeekTicketMain.Version + " §8最新版本:§f " + CheckUpdate.getNewVersion.get("New"));
                        eventPlayer.sendMessage("§8[§3§lGeekTicket§8] §8Found a new version to be updated, the current version:§f " + GeekTicketMain.Version + " §8New:§f " + CheckUpdate.getNewVersion.get("New"));
                    }
                }
                PlayerDataConstructor data = DataManager.getMapData.get(uuid);
                if (data == null) {
                    DataManager.UpPlayerData(uuid);
                    return;
                }
                if (data.TICKET > 0 && ConfigManager.getConfig().getBoolean("auto_clear")) {
                    int day = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("dd")));
                    eventPlayer.sendMessage(Language.PLUGIN_NAME + Language.JOIN_SMG + " §7今天是: " + day + " §7号" );
                }
            }
        }.runTaskAsynchronously(GeekTicketMain.instance);
    }
}
