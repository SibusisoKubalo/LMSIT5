package org.example.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// Mika'il Vallie 230259200
// Log:BookUpdate
//    :BuilderAdd
//    :GenreAdd

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String title;
    private String subject;
    private String author;
    private String genre;

    private int totalCopies;
    private int availableCopies;
    private String location;
    private String status; // e.g., AVAILABLE, ON_LOAN, RESERVED, DAMAGED

    private Book() {}

    private Book(Builder builder) {
        this.title = builder.title;
        this.bookId = builder.bookId;
        this.subject = builder.subject;
        this.author = builder.author;

        this.genre = builder.genre;

        this.totalCopies = builder.totalCopies;
        this.availableCopies = builder.availableCopies;
        this.location = builder.location;
        this.status = builder.status;

    }


    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGenre() { return genre; }   // <-- GETTER
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", bookId=" + bookId +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void displayBook() {
        System.out.println(this);
    }

    public static class Builder {
        private String title, subject, author, genre;
        private int bookId;
        private int totalCopies;
        private int availableCopies;
        private String location;
        private String status;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder bookId(int bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder totalCopies(int totalCopies) {
            this.totalCopies = totalCopies;
            return this;
        }
        public Builder availableCopies(int availableCopies) {
            this.availableCopies = availableCopies;
            return this;
        }
        public Builder location(String location) {
            this.location = location;
            return this;
        }
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}