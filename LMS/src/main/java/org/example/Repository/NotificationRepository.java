package org.example.Repository;

import org.example.Domain.Notification;
import org.example.Util.DBConnection;

import java.sql.*;

public class NotificationRepository {
    public void save(Notification notification) {
        String sql = "INSERT INTO notifications (notificationId, content) VALUES (?, ?) ON DUPLICATE KEY UPDATE content=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notification.getNotificationId());
            stmt.setString(2, notification.getContent());
            stmt.setString(3, notification.getContent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Notification findById(int notificationId) {
        String sql = "SELECT * FROM notifications WHERE notificationId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Notification.Builder()
                        .notificationId(rs.getInt("notificationId"))
                        .content(rs.getString("content"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int notificationId) {
        String sql = "DELETE FROM notifications WHERE notificationId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}