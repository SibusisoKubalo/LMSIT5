package org.example.Repository;
/**
 * LibraryRepository.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;
import org.example.Util.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LibraryRepository {
    public Library save(Library library) {
        String sql = "INSERT INTO libraries (id, name, username, password, num, email) VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE name=?, username=?, password=?, num=?, email=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, library.getId());
            stmt.setString(2, library.getName());
            stmt.setString(3, library.getUsername());
            stmt.setString(4, library.getPassword());
            stmt.setInt(5, library.getNum());
            stmt.setString(6, library.getEmail());
            stmt.setString(7, library.getName());
            stmt.setString(8, library.getUsername());
            stmt.setString(9, library.getPassword());
            stmt.setInt(10, library.getNum());
            stmt.setString(11, library.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return library;
    }

    public Library findById(int id) {
        String sql = "SELECT * FROM libraries WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Library(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("num"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Library update(Library library) {
        return save(library);
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM libraries WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Integer, Library> findAll() {
        Map<Integer, Library> libraryMap = new HashMap<>();
        String sql = "SELECT * FROM libraries";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Library library = new Library(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("num"),
                        rs.getString("email")
                );
                libraryMap.put(library.getId(), library);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libraryMap;
    }
}