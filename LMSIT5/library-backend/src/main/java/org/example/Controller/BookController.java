package org.example.Controller;

import org.example.Domain.Book;
import org.example.Domain.BorrowTransaction;
import org.example.Service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Everyone can see all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Add book (LIBRARIAN only)
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        boolean success = bookService.addBook(book);
        return success
                ? ResponseEntity.ok(Map.of("message", "Book added successfully!"))
                : ResponseEntity.badRequest().body(Map.of("error", "Failed to add book"));
    }

    // Delete book (LIBRARIAN only)
    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id)
                ? ResponseEntity.ok(Map.of("message", "Book deleted!"))
                : ResponseEntity.status(404).body(Map.of("error", "Book not found"));
    }

    // Borrow book (CUSTOMER only)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<?> borrowBook(
            @PathVariable int bookId,
            @RequestParam String username,
            @RequestParam(defaultValue = "7") int days
    ) {
        try {
            boolean success = bookService.borrowBook(bookId, username, days);
            if (!success) {
                return ResponseEntity.badRequest().body(Map.of("error", "Cannot borrow this book"));
            }
            return ResponseEntity.ok(Map.of("message", "Book borrowed successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Return book (CUSTOMER only)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/return/{transactionId}")
    public ResponseEntity<?> returnBook(@PathVariable Long transactionId) {
        try {
            boolean success = bookService.returnBook(transactionId);
            if (!success) {
                return ResponseEntity.badRequest().body(Map.of("error", "Cannot return this book"));
            }
            return ResponseEntity.ok(Map.of("message", "Book returned successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Get borrowed books for a specific user (CUSTOMER only)
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/borrowed")
    public List<BorrowTransaction> getBorrowedBooks(@RequestParam String username) {
        return bookService.getBorrowedBooks(username);
    }
}
