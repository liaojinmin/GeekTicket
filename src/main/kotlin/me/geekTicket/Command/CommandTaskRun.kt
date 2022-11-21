package me.geekTicket.Command

import me.geekTicket.StartModule
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand


object CommandTaskRun: CommandExp {

    override val command = subCommand {
        dynamic {
            suggestion<CommandSender>(uncheck = true) { _, _ ->
                return@suggestion listOf("save","clear")
            }
            //exc
            execute<CommandSender> { sender, context, argument ->
                if (argument.equals("save")) {
                    StartModule.Task.onSave()
                    return@execute sender.sendMessage("执行保存任务")
                }
                if (argument.equals("clear") && sender.isOp) {
                    StartModule.Task.onClearAllTicket()
                    return@execute sender.sendMessage("执行清除任务")
                }
            }
        }
    }
}