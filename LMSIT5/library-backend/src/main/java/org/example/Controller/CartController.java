package org.example.Controller;

import org.example.Domain.Book;
import org.example.Domain.User;
import org.example.Service.CartService;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> addBookToCart(@PathVariable int bookId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        cartService.addBookToCart(user, bookId);
        return ResponseEntity.ok("Book added to cart");
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<?> removeBookFromCart(@PathVariable int bookId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        cartService.removeBookFromCart(user, bookId);
        return ResponseEntity.ok("Book removed from cart");
    }

    @GetMapping
    public ResponseEntity<List<Book>> getCart(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(cartService.getCartItems(user));
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getCartTotal(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(cartService.getCartTotal(user));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(@RequestParam(defaultValue = "14") int days, Authentication authentication) {
        User user = getCurrentUser(authentication);
        cartService.checkoutCart(user, days);
        // Demo payment: simulate payment success
        double total = cartService.getCartTotal(user);
        String message = "Checkout successful. Books borrowed. Payment of R" + total + " processed (demo).";
        return ResponseEntity.ok(message);
    }
}
