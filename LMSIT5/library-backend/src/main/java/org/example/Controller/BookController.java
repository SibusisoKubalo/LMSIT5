package org.example.Controller;

import org.example.Domain.Book;
import org.example.Service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ✅ Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // ✅ Add book (LIBRARIAN only)
    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        boolean success = bookService.addBook(book);
        return success ? "Book added successfully!" : "Failed to add book.";
    }


    // ✅ Find by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.findBook(id);
    }

    // ✅ Delete book (LIBRARIAN only)
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id) ? "Book deleted!" : "Book not found.";
    }

    // ✅ Borrow book (CUSTOMER only)
    @PostMapping("/borrow/{id}")
    public String borrowBook(@PathVariable int id) {
        boolean success = bookService.borrowBook(id);
        return success ? "Book borrowed successfully!" : "Failed to borrow book.";
    }
}
