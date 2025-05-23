package org.example.Domain;

// Ayrton Williams 220086168

import java.util.ArrayList;
import java.util.List;

public class LibraryDatabase {
    private List<Book> books = new ArrayList<>();

    private LibraryDatabase(Builder builder) {
        this.books = builder.books;
    }

    public static class Builder {
        private List<Book> books = new ArrayList<>();

        public Builder addBook(Book book) {
            this.books.add(book);
            return this;
        }

        public LibraryDatabase build() {
            return new LibraryDatabase(this);
        }
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book);
    }

    public void deleteBook(Book book) {
        books.remove(book);
        System.out.println("Book removed: " + book);
    }

    public void updateBook() {
        System.out.println("Updating book records...");
    }

    public List<Book> searchBook(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.toString().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        System.out.println("Searching for book: " + title);
        return results;
    }

    public void displayBooks() {
        for (Book book : books) {
            book.displayBook();
        }
    }

    @Override
    public String toString() {
        return "LibraryDatabase{" +
                "books=" + books +
                '}';
    }
}
