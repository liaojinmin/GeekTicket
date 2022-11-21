package me.geekTicket.Utils.Placeholder

import me.geekTicket.GeekTicketMain
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.geekTicket.Action.ActionManage
import me.geekTicket.Utils.AutoMatic.Task
import org.bukkit.entity.Player

class Papi_Hook : PlaceholderExpansion() {
    var index = 0
    override fun onPlaceholderRequest(p: Player, s: String): String {
        if (s != "ticket") {
            index = s.replace("name_local_", "")
                .replace("ticket_local_", "")
                .replace("name_global_", "")
                .replace("ticket_global_", "")
                .toInt()
        }
        return when (s) {
            "ticket" -> {
                if (!ActionManage.TicketHashMap.isEmpty()) {
                    val d = ActionManage.TicketHashMap[p.uniqueId] ?: return "0"
                    return d.TICKET.toString()
                }
                "0"
            }
            "name_local_$index" -> {
                if (!Task.LocalTop.isEmpty()) {
                    val var1 = Task.LocalTop[index] ?: return "no data"
                    return var1.name
                }
                "null"
            }
            "ticket_local_$index" -> {
                if (!Task.LocalTop.isEmpty()) {
                    val var2 = Task.LocalTop[index] ?: return "no data"
                    return var2.ticket.toString()
                }
                "null"
            }
            "name_global_$index" -> {
                if (!Task.GlobalTop.isEmpty()) {
                    val var1 = Task.GlobalTop[index] ?: return "no data"
                    return var1.name
                }
            "null"
            }
            "ticket_global_$index" -> {
            if (!Task.GlobalTop.isEmpty()) {
                val var2 = Task.GlobalTop[index] ?: return "no data"
                return var2.ticket.toString()
            }
            "null"
        }
            else -> "null"
        }
    }

    override fun getIdentifier(): String {
        return "geekt"
    }

    override fun getAuthor(): String {
        return "HSDLao_Liao"
    }

    override fun getVersion(): String {
        return GeekTicketMain.Version
    }

    override fun persist(): Boolean {
        return true
    }
}
