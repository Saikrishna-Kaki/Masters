package com.stock.management.system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDatabaseManager {

    private Connection connection;

    public void getMysqlConnection(String username, String password) {
        try {
            String url = "jdbc:mysql://localhost:3306/stock_management";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addStock(Stock stock) {
        try {
            String sql = "INSERT INTO stock (tagID, name, available_quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, stock.getTagID());
            stmt.setString(2, stock.getName());
            stmt.setInt(3, stock.getAvailableQuantity());
            stmt.setDouble(4, stock.getPrice());

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Stock getStock(String tagID) {
        try {
            String sql = "SELECT * FROM stock WHERE tagID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tagID);

            ResultSet rs = stmt.executeQuery();
            Stock stock = null;

            if (rs.next()) {
                String name = rs.getString("name");
                int availableQuantity = rs.getInt("available_quantity");
                double price = rs.getDouble("price");
                stock = new Stock(tagID, name, availableQuantity, price);
            }

            rs.close();
            stmt.close();
            return stock;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStock(String tagID, int availableQuantity) {
        try {
            // Check if the stock exists
            Stock existingStock = getStock(tagID);
            if (existingStock != null) {
                // Update the availableQuantity of the existing stock
                existingStock.setAvailableQuantity(availableQuantity);

                // Perform the database update
                String sql = "UPDATE stock SET available_quantity = ? WHERE tagID = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, availableQuantity);
                stmt.setString(2, tagID);
                stmt.executeUpdate();
                stmt.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStock(String tagID) {
        try {
            String sql = "DELETE FROM stock WHERE tagID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tagID);

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        try {
            String sql = "SELECT * FROM stock";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tagID = rs.getString("tagID");
                String name = rs.getString("name");
                int availableQuantity = rs.getInt("available_quantity");
                double price = rs.getDouble("price");
                Stock stock = new Stock(tagID, name, availableQuantity, price);
                stocks.add(stock);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}
