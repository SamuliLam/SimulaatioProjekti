package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PalvelupisteDAO {
    private Connection connection;

    public PalvelupisteDAO(Connection connection) {
        this.connection = connection;
    }

    public void savePalvelupisteData(String palvelupisteName, int servedCustomers, double averageServiceTime) throws SQLException {
        String sql = "INSERT INTO Palvelupiste (name, served_customers, average_service_time) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, palvelupisteName);
            statement.setInt(2, servedCustomers);
            statement.setDouble(3, averageServiceTime);

            statement.executeUpdate();
        }
    }
}
