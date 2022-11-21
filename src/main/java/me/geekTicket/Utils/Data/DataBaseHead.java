package me.geekTicket.Utils.Data;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataBaseHead {


    public abstract Connection getConnection() throws SQLException;

    public abstract void load();

    public abstract void stop();

}
