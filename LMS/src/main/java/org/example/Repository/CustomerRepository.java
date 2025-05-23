package org.example.Repository;

import org.example.Domain.Customer;
import org.example.Util.DBConnection;

import java.sql.*;

public class CustomerRepository {
    public void save(Customer customer) {
        String sql = "INSERT INTO customers (username, password) VALUES (?, ?) ON DUPLICATE KEY UPDATE password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());
            stmt.setString(3, customer.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer findByUsername(String username) {
        String sql = "SELECT * FROM customers WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer.Builder()
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String username) {
        String sql = "DELETE FROM customers WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}