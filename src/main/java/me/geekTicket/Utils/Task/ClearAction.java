package me.geekTicket.Utils.Task;

import me.geekTicket.GeekTicketMain;
import me.geekTicket.Utils.Data.DataManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class ClearAction {



    public static void Run(String s) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        String day = date.format(formatter);
        if (day.equals(s)) {
            try (Connection connection = DataManager.getConnection()) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("truncate table `roll_data`;");
                }
                GeekTicketMain.say("数据库已清空");
            } catch (SQLException e) {
                e.printStackTrace();
                GeekTicketMain.say("数据清空时发生错误-TimeRun");
            }
        }
    }
}
