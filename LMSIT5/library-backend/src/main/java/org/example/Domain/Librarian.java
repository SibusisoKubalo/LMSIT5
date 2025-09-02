package org.example.Domain;

/**
 * Librarian.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Librarian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    private Librarian() {}

    // Builder constructor
    private Librarian(Builder builder) {
        this.id = builder.id;
        setName(builder.name);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    // Methods for librarian actions
    public void addBook() {
        System.out.println("Book added successfully.");
    }

    public void searchBook(String bookTitle) {
        if (bookTitle == null || bookTitle.isEmpty()) {
            System.out.println("Book title cannot be null or empty.");
        } else {
            System.out.println("Searching for book: " + bookTitle);
        }
    }

    public void updateBook() {
        System.out.println("Updating book details...");
    }

    public void issueBook() {
        System.out.println("Issuing book to a member...");
    }

    public void reserveBook() {
        System.out.println("Reserving book for a member...");
    }

    public void returnBook() {
        System.out.println("Processing book return...");
    }

    public void verifyMember(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username cannot be null or empty.");
        } else {
            System.out.println("Verifying member: " + username);
        }
    }

    public void logout() {
        System.out.println("Librarian logged out successfully.");
    }

    // Builder pattern for flexible creation
    public static class Builder {
        private int id;
        private String name;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Librarian build() {
            return new Librarian(this);
        }
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}