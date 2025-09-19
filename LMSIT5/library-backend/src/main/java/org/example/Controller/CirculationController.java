package org.example.Controller;

import org.example.Domain.BorrowTransaction;
import org.example.Domain.Book;
import org.example.Domain.Customer;
import org.example.Domain.Reservation;
import org.example.Service.BorrowTransactionService;
import org.example.Service.ReservationService;
import org.example.Service.BookService;
import org.example.Service.CustomerService;

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
    private CustomerService customerService;

    // Borrow a book
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable int bookId,
                                        @RequestParam(defaultValue = "7") int days) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Book book = bookService.findBook(bookId);
        if (book == null)
            return ResponseEntity.status(404).body(Map.of("error", "Book not found"));

        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

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

        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        Book book = bookService.findBook(bookId);
        if (book == null)
            return ResponseEntity.status(404).body(Map.of("error", "Book not found"));

        Reservation reservation = reservationService.reserveBook(customer, book);
        return ResponseEntity.ok(reservation);
    }

    // Borrow history
    @GetMapping("/history")
    public ResponseEntity<?> getBorrowHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        List<BorrowTransaction> history = borrowTransactionService.getTransactionsByCustomer(customer.getCustomerId());
        return ResponseEntity.ok(history);
    }

    // Reservations
    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        List<Reservation> reservations = reservationService.getReservationsByCustomer(customer.getCustomerId());
        return ResponseEntity.ok(reservations);
    }
}
