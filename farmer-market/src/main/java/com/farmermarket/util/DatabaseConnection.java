package com.farmermarket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/farmermarket"; // Your MySQL DB
    private static final String USERNAME = "root"; // Change if different
    private static final String PASSWORD = "Qwerty@123"; // Replace with your MySQL password

    // Get database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Initialize database with required tables
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "full_name VARCHAR(100) NOT NULL," +
                    "email VARCHAR(100)," +
                    "phone VARCHAR(20)," +
                    "address TEXT," +
                    "user_type VARCHAR(50) NOT NULL" +
                    ")";
            stmt.execute(createUsersTable);

            // Create products table
            String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "farmer_id INT NOT NULL," +
                    "name VARCHAR(100) NOT NULL," +
                    "description TEXT," +
                    "price DOUBLE NOT NULL," +
                    "quantity INT NOT NULL," +
                    "category VARCHAR(50)," +
                    "FOREIGN KEY (farmer_id) REFERENCES users(id)" +
                    ")";
            stmt.execute(createProductsTable);

            // Create orders table
            String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "customer_id INT NOT NULL," +
                    "order_date DATETIME NOT NULL," +
                    "total_amount DOUBLE NOT NULL," +
                    "status VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (customer_id) REFERENCES users(id)" +
                    ")";
            stmt.execute(createOrdersTable);

            // Create order_items table
            String createOrderItemsTable = "CREATE TABLE IF NOT EXISTS order_items (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "order_id INT NOT NULL," +
                    "product_id INT NOT NULL," +
                    "quantity INT NOT NULL," +
                    "price DOUBLE NOT NULL," +
                    "FOREIGN KEY (order_id) REFERENCES orders(id)," +
                    "FOREIGN KEY (product_id) REFERENCES products(id)" +
                    ")";
            stmt.execute(createOrderItemsTable);

            System.out.println("MySQL database initialized successfully");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
