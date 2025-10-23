package org.example.Controller;

import org.example.Domain.BorrowTransaction;
import org.example.Service.BookService;
import org.example.Repository.BorrowTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/viewer")
@CrossOrigin(origins = "http://localhost:5173")
public class BookViewerController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowTransactionRepository borrowTransactionRepository;

    // Check if user can access a book (has borrowed it and not returned)
    @GetMapping("/access/{bookId}")
    public ResponseEntity<?> checkBookAccess(@PathVariable int bookId, @RequestParam String username) {
        try {
            // Find active borrow transactions for this user and book
            List<BorrowTransaction> transactions = borrowTransactionRepository.findByBook_BookId(bookId);

            boolean hasAccess = transactions.stream()
                .anyMatch(transaction ->
                    transaction.getUser().getUsername().equals(username) &&
                    transaction.getReturnDate() == null // Book not returned yet
                );

            if (hasAccess) {
                // Get book details for viewing
                var book = bookService.findBook(bookId);
                if (book != null && book.getPdfUrl() != null) {
                    return ResponseEntity.ok(Map.of(
                        "hasAccess", true,
                        "pdfUrl", book.getPdfUrl(),
                        "title", book.getTitle(),
                        "author", book.getAuthor()
                    ));
                }
            }

            return ResponseEntity.ok(Map.of("hasAccess", false));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to check book access: " + e.getMessage()));
        }
    }

    // Get secure viewing URL (could implement token-based access here)
    @GetMapping("/secure/{bookId}")
    public ResponseEntity<?> getSecureViewingUrl(@PathVariable int bookId, @RequestParam String username) {
        try {
            // Verify access first
            List<BorrowTransaction> transactions = borrowTransactionRepository.findByBook_BookId(bookId);

            boolean hasAccess = transactions.stream()
                .anyMatch(transaction ->
                    transaction.getUser().getUsername().equals(username) &&
                    transaction.getReturnDate() == null
                );

            if (!hasAccess) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied. You must borrow this book first."));
            }

            var book = bookService.findBook(bookId);
            if (book == null || book.getPdfUrl() == null) {
                return ResponseEntity.status(404).body(Map.of("error", "Book content not available"));
            }

            // Return viewing URL with access verification
            return ResponseEntity.ok(Map.of(
                "viewingUrl", book.getPdfUrl(),
                "title", book.getTitle(),
                "author", book.getAuthor(),
                "message", "Access granted for viewing only"
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to get viewing access: " + e.getMessage()));
        }
    }
}
