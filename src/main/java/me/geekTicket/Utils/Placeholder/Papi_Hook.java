package me.geekTicket.Utils.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;
import me.geekTicket.Utils.Data.LeaderboardConstructor;
import org.bukkit.entity.Player;

public class Papi_Hook extends PlaceholderExpansion {

    public Papi_Hook(GeekTicketMain plugin) {
    }
    public String onPlaceholderRequest(Player p, String s) {
        String uuid = String.valueOf(p.getUniqueId());
        switch (s) {
            case ("ticket"):
                if (!DataManager.getMapData.isEmpty()) {
                    PlayerDataConstructor d = DataManager.getMapData.get(uuid);
                    if (d == null) return "0";
                    return  String.valueOf(d.TICKET);
                }
                return "0";
            case ("name_1"):
                LeaderboardConstructor var1 = DataManager.roil_top.get(1);
                if (var1 == null) {
                    return  "";
                }
                return var1.name;
            case ("name_2"):
                LeaderboardConstructor var2 = DataManager.roil_top.get(2);
                if (var2 == null) {
                    return  "";
                }
                return var2.name;
            case ("name_3"):
                LeaderboardConstructor var3 = DataManager.roil_top.get(3);
                if (var3 == null) {
                    return  "";
                }
                return var3.name;
            case ("name_4"):
                LeaderboardConstructor var4 = DataManager.roil_top.get(4);
                if (var4 == null) {
                    return  "";
                }
                return var4.name;
            case ("name_5"):
                LeaderboardConstructor var5 = DataManager.roil_top.get(5);
                if (var5 == null) {
                    return  "";
                }
                return var5.name;
            case ("name_6"):
                LeaderboardConstructor var6 = DataManager.roil_top.get(6);
                if (var6 == null) {
                    return  "";
                }
                return var6.name;
            case ("name_7"):
                LeaderboardConstructor var7 = DataManager.roil_top.get(7);
                if (var7 == null) {
                    return  "";
                }
                return var7.name;
            case ("name_8"):
                LeaderboardConstructor var8 = DataManager.roil_top.get(8);
                if (var8 == null) {
                    return  "";
                }
                return var8.name;
            case ("name_9"):
                LeaderboardConstructor var9 = DataManager.roil_top.get(9);
                if (var9 == null) {
                    return  "";
                }
                return var9.name;
            case ("name_10"):
                LeaderboardConstructor var10 = DataManager.roil_top.get(10);
                if (var10 == null) {
                    return  "";
                }
                return var10.name;
            case ("ticket_1"):
                LeaderboardConstructor var010 = DataManager.roil_top.get(1);
                if (var010 == null) {
                    return  "";
                }
                return String.valueOf(var010.ticket);
            case ("ticket_2"):
                LeaderboardConstructor var012 = DataManager.roil_top.get(2);
                if (var012 == null) {
                    return  "";
                }
                return String.valueOf(var012.ticket);
            case ("ticket_3"):
                LeaderboardConstructor var013 = DataManager.roil_top.get(3);
                if (var013 == null) {
                    return  "";
                }
                return String.valueOf(var013.ticket);
            case ("ticket_4"):
                LeaderboardConstructor var014 = DataManager.roil_top.get(4);
                if (var014 == null) {
                    return  "";
                }
                return String.valueOf(var014.ticket);
            case ("ticket_5"):
                LeaderboardConstructor var015 = DataManager.roil_top.get(5);
                if (var015 == null) {
                    return  "";
                }
                return String.valueOf(var015.ticket);
            case ("ticket_6"):
                LeaderboardConstructor var016 = DataManager.roil_top.get(6);
                if (var016 == null) {
                    return  "";
                }
                return String.valueOf(var016.ticket);
            case ("ticket_7"):
                LeaderboardConstructor var017 = DataManager.roil_top.get(7);
                if (var017 == null) {
                    return  "";
                }
                return String.valueOf(var017.ticket);
            case ("ticket_8"):
                LeaderboardConstructor var018 = DataManager.roil_top.get(8);
                if (var018 == null) {
                    return  "";
                }
                return String.valueOf(var018.ticket);
            case ("ticket_9"):
                LeaderboardConstructor var019 = DataManager.roil_top.get(9);
                if (var019 == null) {
                    return  "";
                }
                return String.valueOf(var019.ticket);
            case ("ticket_10"):
                LeaderboardConstructor var011 = DataManager.roil_top.get(10);
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
