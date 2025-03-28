package org.example.Factory;

import org.example.Domain.Book;

public class BookFactory {
    public static Book createBook(String title, int bookId, String subject, String author) {
        return new Book(title, bookId, subject, author);
    }
}
