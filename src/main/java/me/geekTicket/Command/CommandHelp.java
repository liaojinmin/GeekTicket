package me.geekTicket.Command;

import me.geekTicket.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHelp {

    public static void execute(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 0 || args[0].equals("help"))
        {
            sender.sendMessage("§lGeekTicket §8- §7§l帮助导航 §8- §f§lBate");
            for(String list_to_string : Language.getHelp())
            {
                sender.sendMessage(list_to_string);
            }
        }
    }
}
