package org.example.Domain;

import java.util.ArrayList;
import java.util.List;

public class LibraryDatabase {
    private List<Book> books = new ArrayList<>();

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

    public void searchBook(String title) {
        System.out.println("Searching for book: " + title);
    }

    public void displayBooks() {
        for (Book book : books) {
            book.displayBook();
        }
    }
}
