package me.geekTicket.Command;

import me.geekTicket.Utils.Bukkit.ConfigManager;
import me.geekTicket.Utils.Bukkit.LangLoad;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHelp {

    public static void execute(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 0 || args[0].equals("help"))
        {
            sender.sendMessage("§lGeekTicket §8- §7§l帮助导航 §8- §f§lBate");
            for(String list_to_string : ConfigManager.HELP)
            {
                sender.sendMessage(list_to_string);
            }
        }
    }
}
