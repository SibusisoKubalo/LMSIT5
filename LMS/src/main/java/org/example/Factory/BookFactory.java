package org.example.Factory;

import org.example.Domain.Book;

//Mika'il Vallie 230259200
// Log:BookFactory

public class BookFactory {

    public static Book createBook(String title, String subject, String author, int bookId) {
        return new Book.Builder()
                .title(title)
                .subject(subject)
                .author(author)
                .bookId(bookId)
                .build();
    }

    public static Book createBookWithTitleAndAuthor(String title, String author) {
        return new Book.Builder()
                .title(title)
                .author(author)
                .build();
    }

    public static Book createEmptyBook() {
        return new Book.Builder().build();
    }
}