//product add karne ke liye
package com.farmermarket.dao;

import com.farmermarket.model.Product;
import com.farmermarket.util.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class ProductDAO {
    public void addProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO products (farmer_id, name, description, price, quantity, category) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, product.getFarmerId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getQuantity());
            stmt.setString(6, product.getCategory());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE products SET name=?, description=?, price=?, quantity=?, category=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getQuantity());
            stmt.setString(5, product.getCategory());
            stmt.setInt(6, product.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM products WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProductsByFarmerId(int farmerId) {
        List<Product> productList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE farmer_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, farmerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getInt("farmer_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("quantity"),
                    rs.getString("category")
                );
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
}

