package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDbConnection {
    private static Connection conn = null;
    public static Connection getConnection() {
        if (conn==null) {
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mariadb://mysql.metropolia.fi:3306/rasmusjo?user=rasmusjo&password=pannukakku12");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
        else {
            return conn;
        }
    }
}
