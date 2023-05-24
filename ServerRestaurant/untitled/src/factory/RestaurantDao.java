package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RestaurantDao {
    public static Connection getConnection() {
        String url, user, passwd;
        url = "jdbc:postgresql://localhost:5432/db-restaurants";
        user = "postgres";
        passwd = "0000";

        try {
            return DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
