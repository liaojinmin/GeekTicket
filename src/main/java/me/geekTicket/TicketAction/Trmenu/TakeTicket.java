package me.geekTicket.TicketAction.Trmenu;

import me.arasple.mc.trmenu.api.action.ActionHandle;
import me.arasple.mc.trmenu.api.action.base.ActionBase;
import me.arasple.mc.trmenu.api.action.base.ActionContents;
import me.arasple.mc.trmenu.taboolib.common.platform.ProxyPlayer;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.TicketAction.ActionManager;

import java.util.UUID;

public class TakeTicket extends ActionBase {

    public TakeTicket(ActionHandle handle) {
        super(handle);
    }


    @Override
    public void onExecute(ActionContents contents, ProxyPlayer player, ProxyPlayer placeholderPlayer) {
        int amount = Integer.parseInt(String.valueOf(contents));
        if (player.isOnline()) {
            String name = player.getName();
            UUID uuid = player.getUniqueId();
            GeekTicketMain.say("§8[§3§lGeekTicket§8] §7通过 §bTrMenu §7扣除玩家§f "+name+" §7月券 "+amount);
            ActionManager.takeTicket(uuid, amount);
        }
    }
}
