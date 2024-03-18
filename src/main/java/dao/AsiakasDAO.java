package dao;

import simu.model.Asiakas;
import java.sql.*;
import java.util.List;

public class AsiakasDAO {
    private static Connection connection;

    public AsiakasDAO(Connection connection) {
        this.connection = connection;
    }
    public void saveAllAsiakas(List<Asiakas> asiakasList, int simulationRunNumber) throws SQLException {
        String sql = "INSERT INTO Asiakas (age, saapumisaika, poistumisaika, spent_money, simulation_run_number) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Asiakas asiakas : asiakasList) {
                statement.setInt(1, asiakas.getIka());
                statement.setDouble(2, asiakas.getArrivalTime());
                statement.setDouble(3, asiakas.getDepartureTime());
                statement.setDouble(4, asiakas.getSpentMoney());
                statement.setInt(5, simulationRunNumber);
                statement.addBatch();
            }
            statement.executeBatch();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                for (Asiakas asiakas : asiakasList) {
                    if (generatedKeys.next()) {
                        asiakas.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public void updateSpentMoney(List<Asiakas> asiakasList, int simulationRunNumber) throws SQLException {
        String sql = "UPDATE Asiakas SET spent_money = ? WHERE asiakas_id = ? AND simulation_run_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Asiakas asiakas : asiakasList) {
                statement.setDouble(1, asiakas.getSpentMoney());
                statement.setInt(2, asiakas.getId());
                statement.setInt(3, simulationRunNumber);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public void updatePoistumisaika(List<Asiakas> asiakasList, int simulationRunNumber) throws SQLException {
        String sql = "UPDATE Asiakas SET poistumisaika = ? WHERE asiakas_id = ? AND simulation_run_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Asiakas asiakas : asiakasList) {
                statement.setDouble(1, asiakas.getDepartureTime());
                statement.setInt(2, asiakas.getId());
                statement.setInt(3, simulationRunNumber);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
}
