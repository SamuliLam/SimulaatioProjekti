package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimulationRunDAO {
    private static Connection connection;
    public SimulationRunDAO(Connection connection) {
        this.connection = connection;
    }

    public static int getLastRunNumber() {
        int lastRunNumber = 0;

        try (PreparedStatement statement = connection.prepareStatement("SELECT MAX(run_number) FROM SimulationRun");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                lastRunNumber = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastRunNumber;
    }

    public static void addNewRunNumber() {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO SimulationRun (run_number) VALUES (?)")) {
            int lastRunNumber = getLastRunNumber();
            statement.setInt(1, lastRunNumber + 1);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
