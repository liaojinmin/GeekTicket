package me.geekTicket.Command


import me.geekTicket.Bukkit.LangMain
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand

@CommandHeader(name = "geekt", aliases = ["gkt", "gekt"])
object CommandCore {
    @CommandBody(permission = "geekt.command.give", optional = true)
    val give = CommandGive.command

    @CommandBody(permission = "geekt.command.take", optional = true)
    val take = CommandTake.command

    @CommandBody(permission = "geekt.command.set", optional = true)
    val set = CommandSet.command

    @CommandBody(optional = true)
    val help = CommandHelp.command

    @CommandBody(permission = "geekt.command.reload", optional = true)
    val load = CommandReload.command

    @CommandBody(permission = "geekt.admin", optional = true)
    val taskrun = CommandTaskRun.command


    @CommandBody
    val main = mainCommand {
        execute<CommandSender> { sender, _, argument ->
            if (argument.isEmpty()) {
                sender.sendMessage("§lGeekTicket §8- §7§l帮助导航 §8- §f§lBate")
                for (list_to_string in LangMain.HELP) {
                    sender.sendMessage(list_to_string)
                }
                return@execute
            }
            sender.sendMessage("§8[§3§lGeekTicket§8] §cERROR §3| 未知的参数 §6$argument")
        }
    }
}