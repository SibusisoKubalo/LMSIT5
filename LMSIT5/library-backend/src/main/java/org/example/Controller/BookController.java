package org.example.Controller;

import org.example.Domain.Book;
import org.example.Domain.BorrowTransaction;
import org.example.Service.BookService;
import org.springframework.http.ResponseEntity;
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

    // Add book
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            boolean success = bookService.addBook(book);
            return success
                    ? ResponseEntity.ok(Map.of("message", "Book added successfully!"))
                    : ResponseEntity.badRequest().body(Map.of("error", "Failed to add book"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to add book: " + e.getMessage()));
        }
    }

    // Delete book
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        try {
            boolean result = bookService.deleteBook(id);
            if (result) {
                return ResponseEntity.ok(Map.of("message", "Book deleted successfully!"));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Book not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete book: " + e.getMessage()));
        }
    }

    // Update book status
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateBookStatus(@PathVariable int id, @RequestParam String status) {
        try {
            boolean success = bookService.updateBookStatus(id, status);
            if (success) {
                return ResponseEntity.ok(Map.of("message", "Book status updated to " + status));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Book not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update book status: " + e.getMessage()));
        }
    }

    // Borrow book
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable int bookId, @RequestParam String username, @RequestParam(defaultValue = "14") int days) {
        try {
            boolean success = bookService.borrowBook(bookId, username, days);
            return success
                    ? ResponseEntity.ok(Map.of("message", "Book borrowed successfully!"))
                    : ResponseEntity.badRequest().body(Map.of("error", "Failed to borrow book"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to borrow book: " + e.getMessage()));
        }
    }

    // Get borrowed books by username
    @GetMapping("/borrowed")
    public ResponseEntity<?> getBorrowedBooks(@RequestParam String username) {
        try {
            List<BorrowTransaction> borrowedBooks = bookService.getBorrowedBooks(username);
            return ResponseEntity.ok(borrowedBooks);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to get borrowed books: " + e.getMessage()));
        }
    }

    // Return book
    @PostMapping("/return/{transactionId}")
    public ResponseEntity<?> returnBook(@PathVariable Long transactionId) {
        try {
            boolean success = bookService.returnBook(transactionId);
            return success
                    ? ResponseEntity.ok(Map.of("message", "Book returned successfully!"))
                    : ResponseEntity.badRequest().body(Map.of("error", "Failed to return book"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to return book: " + e.getMessage()));
        }
    }
}
