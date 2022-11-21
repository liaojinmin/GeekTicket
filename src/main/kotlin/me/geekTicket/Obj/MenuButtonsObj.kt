package me.geekTicket.Obj

class MenuButtonsObj(
    val material: String,
    val name: String,
    private val Lore: List<String>,
    val ticket: Int,
    val command: List<String>,
    val deny: String
) {

    fun getlore(): List<String> {
        return Lore
    }
}