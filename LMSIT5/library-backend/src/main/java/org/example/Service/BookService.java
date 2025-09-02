package org.example.Service;

import org.example.Domain.Book;
import org.example.Repository.BookRepository;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();

    public boolean addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(int bookId) {
        return bookRepository.findById(bookId);
    }

    public boolean deleteBook(int bookId) {
        return bookRepository.delete(bookId);
    }
}