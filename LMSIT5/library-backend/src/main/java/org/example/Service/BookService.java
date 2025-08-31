package org.example.Service;

import org.example.Domain.Book;
import org.example.Repository.BookRepository;

public class BookService {
    private final BookRepository bookRepository = new BookRepository();

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public Book findBook(int bookId) {
        return bookRepository.findById(bookId);
    }

    public void deleteBook(int bookId) {
        bookRepository.delete(bookId);
    }
}