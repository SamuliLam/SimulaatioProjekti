package dao;

import simu.model.Palvelupiste;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PalvelupisteDAO {
    private Connection connection;

    public PalvelupisteDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveAllPalvelupisteData(List<Palvelupiste> palvelupisteList, int simulationRunNumber) throws SQLException {
        String sql = "INSERT INTO Palvelupiste (name, served_customers, average_service_time, simulation_run_number) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Palvelupiste palvelupiste : palvelupisteList) {
                statement.setString(1, palvelupiste.getEventTypeToBeScheduled().toString());
                statement.setInt(2, palvelupiste.getServiceTimeSize());
                statement.setDouble(3, palvelupiste.getAverageServiceTime());
                statement.setInt(4, simulationRunNumber);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
}
