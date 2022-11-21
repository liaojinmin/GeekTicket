package me.geekTicket.Action;


import java.util.UUID;

interface ActionImpl {

    void onGivePlayerTicket(UUID PlayerUuid, int amount);

    // take
    void onTakePlayerTicket(UUID PlayerUuid, int amount);

    // set
    void setPlayerTicket(UUID PlayerUuid, int amount);

    // get
    int getPlayerTicket(UUID PlayerUuid);

    /**
     * 从数据库获取玩家的月券数量
     * 如果数据库中没有该玩家数据，则添加默认数据
     * @param uuid 玩家UUID
     */
    int getPlayerData(UUID uuid);

    /**
     * 往数据库写入玩家数据
     * @param PlayerUuid 玩家UUID
     * @param amount 月券数量
     */
    void setPlayerData(UUID PlayerUuid, int amount);

    /**
     * 为玩家添加默认数据
     * @param name 玩家名称
     * @param uuid 玩家UUID
     */
    void AddDefaultData(String name, UUID uuid);
}
