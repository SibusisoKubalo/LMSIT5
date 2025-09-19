package org.example.Service;

import org.example.Domain.BorrowTransaction;
import org.example.Domain.Book;
import org.example.Domain.Customer;
import org.example.Repository.BorrowTransactionRepository;
import org.example.Repository.BookRepository;
import org.example.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowTransactionService {
    @Autowired
    private BorrowTransactionRepository borrowTransactionRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public BorrowTransaction borrowBook(Customer customer, Book book, int days) {
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies for this book.");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        BorrowTransaction transaction = new BorrowTransaction(customer, book, LocalDate.now(), LocalDate.now().plusDays(days));
        return borrowTransactionRepository.save(transaction);
    }

    public BorrowTransaction returnBook(Long transactionId) {
        Optional<BorrowTransaction> opt = borrowTransactionRepository.findById(transactionId);
        if (opt.isEmpty()) throw new RuntimeException("Transaction not found");
        BorrowTransaction transaction = opt.get();
        if (transaction.getReturnDate() != null) throw new RuntimeException("Book already returned");
        transaction.setReturnDate(LocalDate.now());
        // Fine calculation
        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
            long overdueDays = transaction.getReturnDate().toEpochDay() - transaction.getDueDate().toEpochDay();
            transaction.setFine(overdueDays * 5.0); // e.g., R5 per day
        }
        // Update book availability
        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        return borrowTransactionRepository.save(transaction);
    }

    public List<BorrowTransaction> getTransactionsByCustomer(int customerId) {
        return borrowTransactionRepository.findByCustomer_CustomerId(customerId);
    }

    public List<BorrowTransaction> getTransactionsByBook(int bookId) {
        return borrowTransactionRepository.findByBook_BookId(bookId);
    }

    public List<BorrowTransaction> getAllTransactions() {
        return borrowTransactionRepository.findAll();
    }
    public List<BorrowTransaction> getTransactionsByCustomerUsername(String username) {
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);
        if (customerOpt.isEmpty()) return List.of(); // empty list if user not found
        Customer customer = customerOpt.get();
        return borrowTransactionRepository.findByCustomer_CustomerId(customer.getCustomerId());
    }

}

