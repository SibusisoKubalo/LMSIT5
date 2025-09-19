package org.example.Service;

import org.example.Domain.Book;
import org.example.Domain.Customer;
import org.example.Domain.BorrowTransaction;
import org.example.Repository.BookRepository;
import org.example.Repository.CustomerRepository;
import org.example.Repository.BorrowTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BorrowTransactionRepository borrowTransactionRepository;

    // Add a new book
    public boolean addBook(Book book) {
        return bookRepository.save(book) != null;
    }

    // Find book by ID
    public Book findBook(int bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    // Delete book
    public boolean deleteBook(int bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }


    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public List<BorrowTransaction> getBorrowedBooks(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return borrowTransactionRepository.findByCustomer_CustomerId(customer.getCustomerId());
    }

    // Borrow book
    public boolean borrowBook(int bookId, String username, int days) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);

        if (bookOpt.isEmpty() || customerOpt.isEmpty()) return false;

        Book book = bookOpt.get();
        Customer customer = customerOpt.get();

        if (book.getAvailableCopies() <= 0) return false;

        // Update available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Create borrow transaction
        BorrowTransaction transaction = new BorrowTransaction(
                customer,
                book,
                LocalDate.now(),
                LocalDate.now().plusDays(days)
        );
        borrowTransactionRepository.save(transaction);

        return true;
    }

    // Return book
    public boolean returnBook(Long transactionId) {
        Optional<BorrowTransaction> transactionOpt = borrowTransactionRepository.findById(transactionId);
        if (transactionOpt.isEmpty()) return false;

        BorrowTransaction transaction = transactionOpt.get();

        if (transaction.getReturnDate() != null) return false; // Already returned

        transaction.setReturnDate(LocalDate.now());

        // Calculate fine if overdue
        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
            long overdueDays = transaction.getReturnDate().toEpochDay() - transaction.getDueDate().toEpochDay();
            transaction.setFine(overdueDays * 5.0); // e.g., R5/day
        }

        // Update book availability
        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        borrowTransactionRepository.save(transaction);
        return true;
    }
}
