package org.example.Service;

import org.example.Domain.Book;
import org.example.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public boolean addBook(Book book) {
        return bookRepository.save(book) != null;
    }

    public Book findBook(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    public boolean deleteBook(int bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public int getBookCount() {
        return (int) bookRepository.count();
    }
}