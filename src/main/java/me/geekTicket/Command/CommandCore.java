package me.geekTicket.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCore implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            CommandHelp.execute(sender, command, label, args);
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            CommandGive.execute(sender, command, label, args);
        }
        if (args[0].equalsIgnoreCase("take")) {
            CommandTake.execute(sender, command, label, args);
        }

     return true;
    }
}
