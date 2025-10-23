package org.example.Controller;

import org.example.Domain.BorrowTransaction;
import org.example.Domain.Book;
import org.example.Domain.User;
import org.example.Domain.Reservation;
import org.example.Service.BorrowTransactionService;
import org.example.Service.ReservationService;
import org.example.Service.BookService;
import org.example.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/circulation")
@CrossOrigin(origins = "http://localhost:5173")
public class CirculationController {

    @Autowired
    private BorrowTransactionService borrowTransactionService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    // Borrow a book
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable int bookId,
                                        @RequestParam(defaultValue = "7") int days) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Book book = bookService.findBook(bookId);
        if (book == null)
            return ResponseEntity.status(404).body(Map.of("error", "Book not found"));

        User user = userService.findByUsername(username);

        boolean success = bookService.borrowBook(bookId, username, days);
        if (!success)
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot borrow this book"));

        return ResponseEntity.ok(Map.of("message", "Book borrowed successfully!"));
    }

    // Return book
    @PostMapping("/return/{transactionId}")
    public ResponseEntity<?> returnBook(@PathVariable Long transactionId) {
        boolean success = bookService.returnBook(transactionId);
        if (!success)
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot return book"));

        return ResponseEntity.ok(Map.of("message", "Book returned successfully"));
    }

    // Reserve book
    @PostMapping("/reserve/{bookId}")
    public ResponseEntity<?> reserveBook(@PathVariable int bookId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.findByUsername(username);
        Book book = bookService.findBook(bookId);
        if (book == null)
            return ResponseEntity.status(404).body(Map.of("error", "Book not found"));

        // Note: If ReservationService still uses Customer, we'll need to update it too
        // For now, commenting out until ReservationService is updated
        // Reservation reservation = reservationService.reserveBook(user, book);
        // return ResponseEntity.ok(reservation);

        return ResponseEntity.ok(Map.of("message", "Reservation functionality needs to be updated to use User system"));
    }

    // Borrow history
    @GetMapping("/history")
    public ResponseEntity<?> getBorrowHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.findByUsername(username);

        List<BorrowTransaction> history = borrowTransactionService.getTransactionsByUsername(username);
        return ResponseEntity.ok(history);
    }

    // Reservations
    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userService.findByUsername(username);

        // Note: If ReservationService still uses Customer, we'll need to update it too
        // For now, returning empty list until ReservationService is updated
        // List<Reservation> reservations = reservationService.getReservationsByUser(user.getId());

        return ResponseEntity.ok(List.of()); // Empty list for now
    }
}
