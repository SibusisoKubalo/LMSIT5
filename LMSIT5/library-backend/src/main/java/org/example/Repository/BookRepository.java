package org.example.Repository;

import org.example.Domain.Book;
import org.example.Util.DBConnection;

import java.sql.*;

public class BookRepository {
    public void save(Book book) {
        String sql = "INSERT INTO books (bookId, title, subject, author) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE title=?, subject=?, author=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getBookId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getSubject());
            stmt.setString(4, book.getAuthor());
            stmt.setString(5, book.getTitle());
            stmt.setString(6, book.getSubject());
            stmt.setString(7, book.getAuthor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book findById(int bookId) {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book.Builder()
                        .bookId(rs.getInt("bookId"))
                        .title(rs.getString("title"))
                        .subject(rs.getString("subject"))
                        .author(rs.getString("author"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int bookId) {
        String sql = "DELETE FROM books WHERE bookId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}