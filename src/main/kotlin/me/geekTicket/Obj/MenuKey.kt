package me.geekTicket.Obj

import java.util.*

class MenuKey(private val MenuUid: String, private val ButtonsId: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MenuKey) return false
        val p = other
        return p.MenuUid == MenuUid && p.ButtonsId == ButtonsId
    }

    override fun hashCode(): Int {
        return Objects.hash(MenuUid, ButtonsId)
    }
}