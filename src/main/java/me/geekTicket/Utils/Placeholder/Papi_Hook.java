package me.geekTicket.Utils.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.TicketAction.TicketDataManager;
import me.geekTicket.TicketAction.TicketObj;
import me.geekTicket.TicketAction.LeaderboardObj;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Papi_Hook extends PlaceholderExpansion {

    public Papi_Hook(GeekTicketMain plugin) {
    }
    public String onPlaceholderRequest(Player p, String s) {
        UUID uuid = p.getUniqueId();
        switch (s) {
            case ("ticket"):
                if (!TicketDataManager.getTicketMap.isEmpty()) {
                    TicketObj d = TicketDataManager.getTicketMap.get(uuid);
                    if (d == null) return "0";
                    return  String.valueOf(d.TICKET);
                }
                return "0";
            case ("name_1"):
                LeaderboardObj var1 = TicketDataManager.getTicketTopMap.get(1);
                if (var1 == null) {
                    return  "";

                }
                return var1.name;
            case ("name_2"):
                LeaderboardObj var2 = TicketDataManager.getTicketTopMap.get(2);
                if (var2 == null) {
                    return  "";
                }
                return var2.name;
            case ("name_3"):
                LeaderboardObj var3 = TicketDataManager.getTicketTopMap.get(3);
                if (var3 == null) {
                    return  "";
                }
                return var3.name;
            case ("name_4"):
                LeaderboardObj var4 = TicketDataManager.getTicketTopMap.get(4);
                if (var4 == null) {
                    return  "";
                }
                return var4.name;
            case ("name_5"):
                LeaderboardObj var5 = TicketDataManager.getTicketTopMap.get(5);
                if (var5 == null) {
                    return  "";
                }
                return var5.name;
            case ("name_6"):
                LeaderboardObj var6 = TicketDataManager.getTicketTopMap.get(6);
                if (var6 == null) {
                    return  "";
                }
                return var6.name;
            case ("name_7"):
                LeaderboardObj var7 = TicketDataManager.getTicketTopMap.get(7);
                if (var7 == null) {
                    return  "";
                }
                return var7.name;
            case ("name_8"):
                LeaderboardObj var8 = TicketDataManager.getTicketTopMap.get(8);
                if (var8 == null) {
                    return  "";
                }
                return var8.name;
            case ("name_9"):
                LeaderboardObj var9 = TicketDataManager.getTicketTopMap.get(9);
                if (var9 == null) {
                    return  "";
                }
                return var9.name;
            case ("name_10"):
                LeaderboardObj var10 = TicketDataManager.getTicketTopMap.get(10);
                if (var10 == null) {
                    return  "";
                }
                return var10.name;
            case ("ticket_1"):
                LeaderboardObj var010 = TicketDataManager.getTicketTopMap.get(1);
                if (var010 == null) {
                    return  "";
                }
                return String.valueOf(var010.ticket);
            case ("ticket_2"):
                LeaderboardObj var012 = TicketDataManager.getTicketTopMap.get(2);
                if (var012 == null) {
                    return  "";
                }
                return String.valueOf(var012.ticket);
            case ("ticket_3"):
                LeaderboardObj var013 = TicketDataManager.getTicketTopMap.get(3);
                if (var013 == null) {
                    return  "";
                }
                return String.valueOf(var013.ticket);
            case ("ticket_4"):
                LeaderboardObj var014 = TicketDataManager.getTicketTopMap.get(4);
                if (var014 == null) {
                    return  "";
                }
                return String.valueOf(var014.ticket);
            case ("ticket_5"):
                LeaderboardObj var015 = TicketDataManager.getTicketTopMap.get(5);
                if (var015 == null) {
                    return  "";
                }
                return String.valueOf(var015.ticket);
            case ("ticket_6"):
                LeaderboardObj var016 = TicketDataManager.getTicketTopMap.get(6);
                if (var016 == null) {
                    return  "";
                }
                return String.valueOf(var016.ticket);
            case ("ticket_7"):
                LeaderboardObj var017 = TicketDataManager.getTicketTopMap.get(7);
                if (var017 == null) {
                    return  "";
                }
                return String.valueOf(var017.ticket);
            case ("ticket_8"):
                LeaderboardObj var018 = TicketDataManager.getTicketTopMap.get(8);
                if (var018 == null) {
                    return  "";
                }
                return String.valueOf(var018.ticket);
            case ("ticket_9"):
                LeaderboardObj var019 = TicketDataManager.getTicketTopMap.get(9);
                if (var019 == null) {
                    return  "";
                }
                return String.valueOf(var019.ticket);
            case ("ticket_10"):
                LeaderboardObj var011 = TicketDataManager.getTicketTopMap.get(10);
                if (var011 == null) {
                    return  "";
                }
                return String.valueOf(var011.ticket);
            default:
                return "null";
        }
    }

    public String getIdentifier() {
        return "geekt";
    }

    public String getAuthor() {
        return "HSDLao_Liao";
    }

    public String getVersion() {
        return GeekTicketMain.Version;
    }
    public boolean persist() {
        return true;
    }
}
