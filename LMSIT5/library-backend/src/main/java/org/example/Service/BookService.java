package org.example.Service;

import org.example.Domain.Book;
import org.example.Domain.User;
import org.example.Domain.BorrowTransaction;
import org.example.Repository.BookRepository;
import org.example.Repository.UserRepository;
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
    private UserRepository userRepository;

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
        if (!bookRepository.existsById(bookId)) {
            return false;
        }

        // Check if book has any borrow transactions
        List<BorrowTransaction> transactions = borrowTransactionRepository.findByBook_BookId(bookId);
        if (!transactions.isEmpty()) {
            // Check if there are any active (unreturned) borrows
            boolean hasActiveBorrows = transactions.stream()
                    .anyMatch(transaction -> transaction.getReturnDate() == null);

            if (hasActiveBorrows) {
                throw new RuntimeException("Cannot delete book: It is currently borrowed by users");
            }

            // If there are only returned borrows (history), delete the transactions first
            // This handles the foreign key constraint
            borrowTransactionRepository.deleteAll(transactions);
        }

        try {
            bookRepository.deleteById(bookId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete book: " + e.getMessage());
        }
    }

    // Update book status
    public boolean updateBookStatus(int bookId, String status) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isEmpty()) {
            return false;
        }

        Book book = bookOpt.get();
        book.setStatus(status);

        // If marking as damaged or out of service, set available copies to 0
        if ("DAMAGED".equals(status) || "OUT_OF_SERVICE".equals(status)) {
            book.setAvailableCopies(0);
        } else if ("AVAILABLE".equals(status)) {
            // If marking as available, restore available copies to total copies
            book.setAvailableCopies(book.getTotalCopies());
        }

        bookRepository.save(book);
        return true;
    }

    // Mark book as out of service (helper method)
    private boolean markBookAsOutOfService(int bookId) {
        return updateBookStatus(bookId, "OUT_OF_SERVICE");
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get borrowed books by username - now using User entity
    public List<BorrowTransaction> getBorrowedBooks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return borrowTransactionRepository.findByUser_Id(user.getId());
    }

    // Borrow book - now using User entity instead of Customer
    public boolean borrowBook(int bookId, String username, int days) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (bookOpt.isEmpty() || userOpt.isEmpty()) return false;

        Book book = bookOpt.get();
        User user = userOpt.get();

        if (book.getAvailableCopies() <= 0) return false;

        // Update available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Create borrow transaction using User instead of Customer
        BorrowTransaction transaction = new BorrowTransaction(
                user,
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

        // Calculate fine if overdue using book's fine rate
        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
            long overdueDays = transaction.getReturnDate().toEpochDay() - transaction.getDueDate().toEpochDay();
            double dailyFineRate = transaction.getBook().getFineRate() > 0 ? transaction.getBook().getFineRate() : 5.0;
            transaction.setFine(overdueDays * dailyFineRate);
        }

        // Update book availability
        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        borrowTransactionRepository.save(transaction);
        return true;
    }
}
