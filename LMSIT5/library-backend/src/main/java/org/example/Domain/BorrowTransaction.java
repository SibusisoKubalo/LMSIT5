package org.example.Domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BorrowTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;

    public BorrowTransaction() {}

    public BorrowTransaction(User user, Book book, LocalDate borrowDate, LocalDate dueDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.fine = 0.0;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }

    // Calculate current fine based on overdue days and book's fine rate
    public double calculateCurrentFine() {
        if (returnDate != null) {
            // Book has been returned, use stored fine
            return fine;
        }

        // Book is still borrowed, calculate current fine if overdue
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            long overdueDays = today.toEpochDay() - dueDate.toEpochDay();
            return overdueDays * (book != null ? book.getFineRate() : 5.0); // Default 5.0 if no book rate
        }

        return 0.0; // Not overdue
    }
}
