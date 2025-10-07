package org.example.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Domain.User;
import org.example.Repository.UserRepository;
import org.example.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        // Generate JWT
        org.springframework.security.core.userdetails.UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRole())
            .build();
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of(
            "token", jwt,
            "email", user.getEmail(),
            "role", user.getRole()
        ));
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT logout is handled client-side (just delete token)
        return ResponseEntity.ok("Logged out successfully");
    }

    // GET LOGGED-IN USER
    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(null);
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return ResponseEntity.status(401).body(null);
        User user = optionalUser.get();
        return ResponseEntity.ok(Map.of(
            "email", user.getEmail(),
            "role", user.getRole()
        ));
    }

    // SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String name = body.get("name");
        String surname = body.get("surname");
        String email = body.get("email");
        String password = body.get("password");
        String role = body.getOrDefault("role", "CUSTOMER");
        String staffNumber = body.get("staffNumber"); // only for librarian/admin

        try {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                return ResponseEntity.status(409).body("Email already exists");
            }
            // Use UserService to create user
            User newUser = new User(name, surname, email, passwordEncoder.encode(password), role, staffNumber);
            userRepository.save(newUser);

            // Auto-login: create session
            HttpSession session = request.getSession(true);
            session.setAttribute("user", newUser);

            return ResponseEntity.ok(Map.of(
                "email", newUser.getEmail(),
                "role", newUser.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Signup failed: " + e.getMessage());
        }
    }
}
