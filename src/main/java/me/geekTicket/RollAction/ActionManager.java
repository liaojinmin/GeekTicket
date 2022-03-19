package me.geekTicket.RollAction;

import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;

public class ActionManager {

    /**
     * 给予玩家月券
     * @param PlayerName 玩家名称
     * @param amount 需要给予的数量
     */
    public static void giveTicket(String PlayerName, int amount) {
        Ticket.give(PlayerName, amount);
    }

    /**
     * 扣除玩家月券
     * @param PlayerName 玩家名称
     * @param amount 数量
     */
    public static void takeTicket(String PlayerName, int amount) {
        Ticket.take(PlayerName, amount);
    }

    /**
     * 获取玩家拥有的月券
     * @param name 玩家名称
     * @param uuid 玩家uuid
     * @return 返回数量 int
     */
    public int getPlayerTicket(String name, String uuid) {
        return ActionHead.getPlayerRoll(name, uuid);
    }

    /**
     *检查玩家是否拥有指定数量的月券
     *
     * @param name 玩家名称
     * @param uuid uuid
     * @param amt 需要检查的数量
     * @return 返回布尔值
     */
    public static boolean isTicket(String name, String uuid, int amt) {
        if (DataManager.getMapData.isEmpty()) {
            return false;
        }
        PlayerDataConstructor data = DataManager.getMapData.get(uuid);
        if (data == null) return false;
        return data.PLAYER_NAME.equalsIgnoreCase(name) && data.TICKET >= amt;
    }


    private static ActionHead Ticket;
    public ActionManager() {
        load();
    }
    private void load() {
        Ticket = new Action();
    }


}
