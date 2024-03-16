package dao;

import simu.model.Tuotehallinta.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.HashMap;

public class AsiakasOstoslistaDAO {

    // You might have a database connection or a connection pool
    private Connection connection;

    // Constructor that takes a database connection or a connection pool
    public AsiakasOstoslistaDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to save a shopping list to the database
    public void saveShoppingList(List<Item> shoppingList, int simulationRunNumber) throws SQLException {
        String insertSql = "INSERT INTO SoldProducts (item_name, quantity, price, total, simulation_run_number) VALUES (?, ?, ?, ?, ?)";
        String updateSql = "UPDATE SoldProducts SET quantity = quantity + ?, total = total + ? WHERE item_name = ? AND simulation_run_number = ?";

        try {
            for (Item item : shoppingList) {
                double price = getProductPrice(item.getName()); // Lisää tämä rivi hinnan hakemiseen

                // Tarkista ensin, onko rivi jo olemassa
                if (isRowExists(item.getName(), simulationRunNumber)) {
                    // Jos rivi on olemassa, päivitä quantity ja total
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setInt(1, item.getQuantity());
                        updateStatement.setDouble(2, item.getQuantity() * price);
                        updateStatement.setString(3, item.getName());
                        updateStatement.setInt(4, simulationRunNumber);
                        updateStatement.executeUpdate();
                    }
                } else {
                    // Jos riviä ei ole olemassa, lisää uusi rivi
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, item.getName());
                        insertStatement.setInt(2, item.getQuantity());
                        insertStatement.setDouble(3, price);
                        insertStatement.setDouble(4, item.getQuantity() * price);
                        insertStatement.setInt(5, simulationRunNumber);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Käsittele poikkeus sovelluksesi vaatimusten mukaan
        }
    }

    private double getProductPrice(String itemName) throws SQLException {
        String sql = "SELECT hinta FROM Products WHERE tuotenimi = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, itemName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("hinta");
            }
        }

        // Palauta jokin oletushinta, jos tuotetta ei löydy
        return 0.0;
    }


    private boolean isRowExists(String itemName, int simulationRunNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM SoldProducts WHERE item_name = ? AND simulation_run_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, itemName);
            statement.setInt(2, simulationRunNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0;
            }
        }

        return false;
    }





    public HashMap<String, Integer> getSoldProducts(int simulationRunNumber) throws SQLException {
        HashMap<String, Integer> soldProducts = new HashMap<>();
        String sql = "SELECT item_name, quantity FROM SoldProducts WHERE simulation_run_number = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Create a prepared statement
            statement = connection.prepareStatement(sql);

            // Set parameter for the SQL query (simulation run number)
            statement.setInt(1, simulationRunNumber);

            // Execute the SQL query
            resultSet = statement.executeQuery();

            // Loop through the result set
            while (resultSet.next()) {
                // Get item name and quantity from the result set
                String itemName = resultSet.getString("item_name");
                int quantity = resultSet.getInt("quantity");

                // Put the data into the HashMap
                soldProducts.put(itemName, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        } finally {
            // Close the result set and prepared statement
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return soldProducts;}
}
