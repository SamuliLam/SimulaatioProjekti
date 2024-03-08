package dao;

import simu.model.Tuotehallinta.Groceries;
import simu.model.Tuotehallinta.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AsiakasOstoslistaDAO {

    // You might have a database connection or a connection pool
    private static Connection connection;

    // Constructor that takes a database connection or a connection pool
    public AsiakasOstoslistaDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to save a shopping list to the database
    public static void saveShoppingList(int asiakasId, List<Item> shoppingList) throws SQLException {
        // Define your SQL query for inserting the shopping list into the database
        String sql = "INSERT INTO GroceryList (item_name, quantity) VALUES (?, ?)";

        PreparedStatement statement = null;
        try {
            // Create a prepared statement
            statement = connection.prepareStatement(sql);

            // Loop through the shopping list
            for (Item item : shoppingList) {
                // Set parameters for the SQL query (item name, and quantity)
                statement.setString(1, item.getName());
                statement.setInt(2, item.getQuantity());

                // Execute the SQL query
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        } finally {
            // Close the prepared statement
            if (statement != null) {
                statement.close();
            }
        }
    }
}
