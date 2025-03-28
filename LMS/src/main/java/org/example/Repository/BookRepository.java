package org.example.Repository;

import org.example.Domain.Book;
import java.util.HashMap;
import java.util.Map;

public class BookRepository {
    private final Map<Integer, Book> bookMap = new HashMap<>();

    public void save(Book book) {
        bookMap.put(book.getBookId(), book);
    }

    public Book findById(int bookId) {
        return bookMap.get(bookId);
    }

    public void delete(int bookId) {
        bookMap.remove(bookId);
    }
}
