package me.geekTicket.Command

import me.geekTicket.Bukkit.LangMain
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand

object CommandHelp: CommandExp {
    override val command = subCommand {

        execute<CommandSender> { sender, context, argument ->
            sender.sendMessage("§lGeekTicket §8- §7§l帮助导航 §8- §f§lBate")
            for (list_to_string in LangMain.HELP) {
                sender.sendMessage(list_to_string)
            }
        }
    }
}