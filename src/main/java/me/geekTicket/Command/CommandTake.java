package me.geekTicket.Command;

import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.TicketAction.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Scanner;
import java.util.UUID;

public class CommandTake {

    public static void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("geekt.command.take")) {
            sender.sendMessage(ConfigManager.PLUGIN_NAME + ConfigManager.NO_PERM);
            GeekTicketMain.say("§8[§3§lGeekTicket§8]§c geekT.command.take");
            return;
        }
        if (args.length > 2) {
            if (args[0].equals("take")) {
                Player player = Bukkit.getOfflinePlayer(args[1]).getPlayer();
                Scanner arg = new Scanner(args[2]);
                if (player == null) {
                    sender.sendMessage(ConfigManager.PLUGIN_NAME + ConfigManager.IS_ONLINE);
                    return;
                }
                if (!arg.hasNextInt()) {
                    sender.sendMessage(ConfigManager.PLUGIN_NAME + ConfigManager.IS_MATCH);
                } else {
                    UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                    ActionManager.takeTicket(uuid, Integer.parseInt(args[2]));
                    sender.sendMessage(ConfigManager.CONSOLE_TAKE_SMG.replace("[NAME]",args[1]).replace("[TICKET]",args[2]));
                }
            }
            return;
        }
        sender.sendMessage(ConfigManager.PLUGIN_NAME + " §8- §7§l帮助导航 §8- §f§lBate");
        sender.sendMessage("§b§l/geekt take [name] [*] §8- §7扣除目标月券");
    }
}
