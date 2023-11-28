package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/h2cn";
        String user = "user1";
        String password = "asdf";

        return DriverManager.getConnection(url, user, password);
    }
}
