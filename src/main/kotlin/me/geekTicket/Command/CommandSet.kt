package me.geekTicket.Command

import me.geekTicket.Bukkit.LangMain
import me.geekTicket.GeekTicketMain
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import java.util.*


object CommandSet: CommandExp {
    override val command = subCommand {
        //Plauer
        dynamic {
            suggestion<CommandSender>(uncheck = true) { _, _ ->
                Bukkit.getOnlinePlayers().map { it.name }
            }
            //exc
            execute<CommandSender> { sender, context, argument ->
                val args = context.argument(0).split(" ")
                val player = Bukkit.getOfflinePlayer(args[0]).player
                val arg = Scanner(args[1])
                if (player == null) {
                    return@execute sender.sendMessage(LangMain.PLUGIN_NAME + LangMain.IS_ONLINE)
                }
                if (!arg.hasNextInt()) {
                    return@execute sender.sendMessage(LangMain.PLUGIN_NAME + LangMain.IS_MATCH)
                } else {
                    GeekTicketMain.action.setPlayerTicket(player.uniqueId, args[1].toInt())
                    sender.sendMessage(
                        LangMain.CONSOLE_GIVE_SMG.replace("[NAME]", args[0]).replace("[TICKET]", args[1])
                    )
                }
            }
        }
    }
}