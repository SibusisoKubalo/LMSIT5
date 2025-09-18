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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/circulation")
public class CirculationController {
    @Autowired
    private BorrowTransactionService borrowTransactionService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/borrow")
    public BorrowTransaction borrowBook(@RequestParam int customerId, @RequestParam int bookId, @RequestParam(defaultValue = "14") int days) {
        Customer customer = customerService.findCustomer(customerId);
        Book book = bookService.findBook(bookId);
        return borrowTransactionService.borrowBook(customer, book, days);
    }

    @PostMapping("/return")
    public BorrowTransaction returnBook(@RequestParam Long transactionId) {
        return borrowTransactionService.returnBook(transactionId);
    }

    @PostMapping("/reserve")
    public Reservation reserveBook(@RequestParam int customerId, @RequestParam int bookId) {
        Customer customer = customerService.findCustomer(customerId);
        Book book = bookService.findBook(bookId);
        return reservationService.reserveBook(customer, book);
    }

    @GetMapping("/history/{customerId}")
    public List<BorrowTransaction> getBorrowHistory(@PathVariable int customerId) {
        return borrowTransactionService.getTransactionsByCustomer(customerId);
    }

    @GetMapping("/reservations/{customerId}")
    public List<Reservation> getReservations(@PathVariable int customerId) {
        return reservationService.getReservationsByCustomer(customerId);
    }
}
