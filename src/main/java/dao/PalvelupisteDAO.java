package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PalvelupisteDAO {
    private Connection connection;

    public PalvelupisteDAO(Connection connection) {
        this.connection = connection;
    }

    public void savePalvelupisteData(String palvelupisteName, int servedCustomers, double averageServiceTime, int simulationRunNumber) throws SQLException {
        String sql = "INSERT INTO Palvelupiste (name, served_customers, average_service_time, simulation_run_number) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, palvelupisteName);
            statement.setInt(2, servedCustomers);
            statement.setDouble(3, averageServiceTime);
            statement.setInt(4, simulationRunNumber);

            statement.executeUpdate();
        }
    }
}
