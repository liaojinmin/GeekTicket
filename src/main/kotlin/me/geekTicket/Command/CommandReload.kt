package me.geekTicket.Command

import me.geekTicket.Bukkit.ConfigMain
import me.geekTicket.Bukkit.LangMain
import me.geekTicket.Utils.Data.Menu.MenuDataManage
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand


object CommandReload: CommandExp {
    override val command = subCommand {

        dynamic {

            execute<CommandSender> { sender, context, argument ->
                ConfigMain()
                LangMain
                MenuDataManage.LoadAllMenu()
                return@execute
            }
        }
    }
}