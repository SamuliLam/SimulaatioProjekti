package dao;

import simu.model.Asiakas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AsiakasDAO {
    private static Connection connection;

    public AsiakasDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveAllAsiakas(List<Asiakas> asiakasList, int simulationRunNumber) throws SQLException {
        String sql = "INSERT INTO Asiakas (asiakas_id, age, saapumisaika, poistumisaika, spent_money, simulation_run_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Asiakas asiakas : asiakasList) {
                statement.setInt(1, asiakas.getId());
                statement.setInt(2, asiakas.getIka());
                statement.setDouble(3, asiakas.getarrivalTime());
                statement.setDouble(4, asiakas.getdepartureTime());
                statement.setDouble(5, asiakas.getSpentMoney());
                statement.setInt(6, simulationRunNumber);
                statement.addBatch();
            }
            statement.executeBatch();
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
                statement.setDouble(1, asiakas.getdepartureTime());
                statement.setInt(2, asiakas.getId());
                statement.setInt(3, simulationRunNumber);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
}
