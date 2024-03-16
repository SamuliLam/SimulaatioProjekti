package dao;

import simu.model.Asiakas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsiakasDAO {
    private static Connection connection;

    public AsiakasDAO(Connection connection) {
        this.connection = connection;
    }

    // Save an Asiakas to the database
    public void saveAsiakas(Asiakas asiakas, int simulationRunNumber) throws SQLException {
        String sql = "INSERT INTO Asiakas (asiakas_id, age, saapumisaika, poistumisaika, spent_money, simulation_run_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, asiakas.getId());
            statement.setInt(2, asiakas.getIka());
            statement.setDouble(3, asiakas.getarrivalTime());
            statement.setDouble(4, asiakas.getdepartureTime());
            statement.setDouble(5, asiakas.getSpentMoney());
            statement.setInt(6, simulationRunNumber);

            // Execute the query
            statement.executeUpdate();
        }
    }

    public static void updateSpentMoney(int asiakasId, double newSpentMoney, int simulationRunNumber) throws SQLException {
        String sql = "UPDATE Asiakas SET spent_money = ? WHERE asiakas_id = ? AND simulation_run_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newSpentMoney);
            statement.setInt(2, asiakasId);
            statement.setInt(3, simulationRunNumber);

            // Execute the update query
            statement.executeUpdate();
        }
    }

    public static void updatePoistumisaika(int asiakasId, double poistumisaika, int simulationRunNumber) throws SQLException {
        String sql = "UPDATE Asiakas SET poistumisaika = ? WHERE asiakas_id = ? AND simulation_run_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, poistumisaika);
            statement.setInt(2, asiakasId);
            statement.setInt(3, simulationRunNumber);

            // Execute the update query
            statement.executeUpdate();
        }
    }

    public static int getLatestId() {
        String sql = "SELECT MAX(asiakas_id) FROM Asiakas";
        Integer latestId = null; // Initialize the latestId to null

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                latestId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Palauta 0, jos latestId on null, muuten palauta latestId
        return (latestId != null) ? latestId : 0;
    }

}