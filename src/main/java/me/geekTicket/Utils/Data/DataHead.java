package me.geekTicket.Utils.Data;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataHead {


    public abstract Connection getConnection() throws SQLException;

    public abstract void load();

    public abstract void createTables();

    public abstract void stop();

}
