package org.example.Controller;

import org.example.Domain.Book;
import org.example.Service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ✅ Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        // directly from repository since service doesn’t have it yet
        return bookService.getAllBooks();
    }

    // ✅ Add book
    @PostMapping
    public String addBook(@RequestBody Book book) {
        boolean success = bookService.addBook(book);
        return success ? "Book added successfully!" : "Failed to add book.";
    }

    // ✅ Find by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.findBook(id);
    }

    // ✅ Delete book
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id) ? "Book deleted!" : "Book not found.";
    }
}

