package org.example.Service;

import org.example.Domain.BorrowTransaction;
import org.example.Domain.Book;
import org.example.Domain.User;
import org.example.Repository.BorrowTransactionRepository;
import org.example.Repository.BookRepository;
import org.example.Repository.UserRepository;
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
    private UserRepository userRepository;

    public BorrowTransaction borrowBook(User user, Book book, int days) {
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies for this book.");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        BorrowTransaction transaction = new BorrowTransaction(user, book, LocalDate.now(), LocalDate.now().plusDays(days));
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

    public List<BorrowTransaction> getTransactionsByUser(Long userId) {
        return borrowTransactionRepository.findByUser_Id(userId);
    }

    public List<BorrowTransaction> getTransactionsByBook(int bookId) {
        return borrowTransactionRepository.findByBook_BookId(bookId);
    }

    public List<BorrowTransaction> getAllTransactions() {
        return borrowTransactionRepository.findAll();
    }

    public List<BorrowTransaction> getTransactionsByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) return List.of(); // empty list if user not found
        User user = userOpt.get();
        return borrowTransactionRepository.findByUser_Id(user.getId());
    }
}
