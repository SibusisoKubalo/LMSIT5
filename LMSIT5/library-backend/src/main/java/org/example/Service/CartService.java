package org.example.Service;

import org.example.Domain.*;
import org.example.Repository.CartRepository;
import org.example.Repository.BookRepository;
import org.example.Repository.UserRepository;
import org.example.Repository.BorrowTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowTransactionRepository borrowTransactionRepository;

    public Cart getOrCreateCart(User user) {
        Optional<Cart> cartOpt = cartRepository.findByUser(user);
        if (cartOpt.isPresent()) {
            return cartOpt.get();
        } else {
            Cart cart = new Cart(user);
            return cartRepository.save(cart);
        }
    }

    public Cart addBookToCart(User user, int bookId) {
        Cart cart = getOrCreateCart(user);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailableCopies() <= 0) throw new RuntimeException("No available copies");
        cart.addBook(book);
        return cartRepository.save(cart);
    }

    public Cart removeBookFromCart(User user, int bookId) {
        Cart cart = getOrCreateCart(user);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        cart.removeBook(book);
        return cartRepository.save(cart);
    }

    public List<Book> getCartItems(User user) {
        Cart cart = getOrCreateCart(user);
        return cart.getItems();
    }

    public double getCartTotal(User user) {
        Cart cart = getOrCreateCart(user);
        return cart.getItems().stream().mapToDouble(Book::getPrice).sum();
    }

    public void checkoutCart(User user, int days) {
        Cart cart = getOrCreateCart(user);
        if (cart.isCheckedOut()) throw new RuntimeException("Cart already checked out");

        // Use User directly instead of mapping to Customer
        for (Book book : cart.getItems()) {
            if (book.getAvailableCopies() <= 0) throw new RuntimeException("No available copies for " + book.getTitle());
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);

            // Create BorrowTransaction with User instead of Customer
            BorrowTransaction transaction = new BorrowTransaction(
                user,
                book,
                LocalDate.now(),
                LocalDate.now().plusDays(days)
            );
            borrowTransactionRepository.save(transaction);
        }
        cart.setCheckedOut(true);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
