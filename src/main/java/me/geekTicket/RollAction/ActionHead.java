package me.geekTicket.RollAction;

import me.geekTicket.Utils.Data.DataManager;
import me.geekTicket.Utils.Data.PlayerDataConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public abstract class ActionHead {


    public abstract void give(String PlayerName, int amount);
    public abstract void take(String PlayerName, int amount);



    /**
     * c从缓存中获取玩家持有的月卷数量
     * @param name 玩家名称
     * @param uuid uuid名称
     * @return 返回持有数，无则null
     */
    public static int getPlayerRoll(String name, String uuid) {
        //Map 为空，则添加当前玩家的数据。
        if (DataManager.getMapData.isEmpty()) {
            AddDefaultData(name, uuid);
            return 0;
        }
        //获取MAP中的数据。
        PlayerDataConstructor data = DataManager.getMapData.get(uuid);
        if (data == null) return 0;
        if (data.PLAYER_NAME.equalsIgnoreCase(name)) {
            return data.TICKET;
        }
        AddDefaultData(name, uuid);
        return 0;
    }

     private static void AddDefaultData(String name, String uuid) {
        //更新缓存
        PlayerDataConstructor d = new PlayerDataConstructor(name, uuid, 0);
        DataManager.getMapData.put(uuid, d);
        //更新数据库信息
        try (Connection connection = DataManager.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO roll_data(`name`,`uuid`) VALUES(?,?);")) {
                statement.setString(1, name);
                statement.setString(2, uuid);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
