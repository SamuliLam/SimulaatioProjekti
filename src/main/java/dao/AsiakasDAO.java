package dao;

import simu.model.Asiakas;

import java.sql.*;

public class AsiakasDAO {
    private static Connection connection;

    public AsiakasDAO(Connection connection) {
        this.connection = connection;
    }

    // Save an Asiakas to the database
    public void saveAsiakas(Asiakas asiakas) throws SQLException {
        String sql = "INSERT INTO Asiakas (asiakas_id, age, saapumisaika, poistumisaika, spent_money) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, asiakas.getId());
            statement.setInt(2, asiakas.getIka());
            statement.setDouble(3, asiakas.getSaapumisaika());
            statement.setDouble(4, asiakas.getPoistumisaika());
            statement.setDouble(5, asiakas.getSpentMoney());

            // Execute the query
            statement.executeUpdate();
        }
    }

    public static void updateSpentMoney(int asiakasId, double newSpentMoney) throws SQLException {
        String sql = "UPDATE Asiakas SET spent_money = ? WHERE asiakas_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, newSpentMoney);
            statement.setInt(2, asiakasId);

            // Execute the update query
            statement.executeUpdate();
        }
    }

    public static void updatePoistumisaika(int asiakasId, double poistumisaika) throws SQLException {
        String sql = "UPDATE Asiakas SET poistumisaika = ? WHERE asiakas_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, poistumisaika);
            statement.setInt(2, asiakasId);

            // Execute the update query
            statement.executeUpdate();
        }
    }


}


