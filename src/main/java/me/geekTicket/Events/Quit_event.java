package me.geekTicket.Events;

import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit_event implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        String uuid = String.valueOf(p.getUniqueId());
        PlayerDataConstructor d = DataManager.getMapData.get(uuid);
        if (d != null) {
            DataManager.getMapData.remove(uuid);
        }
    }
}
