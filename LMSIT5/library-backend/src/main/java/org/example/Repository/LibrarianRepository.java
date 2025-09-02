package org.example.Repository;
/**
 * LibrarianRepository.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Librarian;
import org.example.Util.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LibrarianRepository {
    public boolean save(Librarian librarian) {
        String sql = "INSERT INTO librarians (id, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, librarian.getId());
            stmt.setString(2, librarian.getName());
            stmt.setString(3, librarian.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Librarian findById(int id) {
        String sql = "SELECT * FROM librarians WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Librarian(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Librarian librarian) {
        return save(librarian);
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM librarians WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<Integer, Librarian> findAll() {
        Map<Integer, Librarian> librarianMap = new HashMap<>();
        String sql = "SELECT * FROM librarians";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Librarian librarian = new Librarian(rs.getInt("id"), rs.getString("name"));
                librarianMap.put(librarian.getId(), librarian);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarianMap;
    }
}