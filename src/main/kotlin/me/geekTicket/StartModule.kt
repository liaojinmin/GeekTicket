package me.geekTicket

import me.geekTicket.Configuration.ConfigManage
import me.geekTicket.Utils.AutoMatic.Task

class StartModule {
    companion object {
        val Task = Task()
        fun onStart() {
            // 定时任务
            Task.onMapHashUp()
            Task.getLocalTop()
            if (ConfigManage.START_BUNGEE && ConfigManage.GLOBAL_TOP) {
                Task.getGlobalTop()
            }
            if (ConfigManage.AUTO_CLEAR) {
                Task.onClearAllTicket()
            }
            if (ConfigManage.AUTO_SAVE) {
                Task.onSave()
            }
        }
    }
}