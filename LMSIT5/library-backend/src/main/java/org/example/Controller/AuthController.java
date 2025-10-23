package org.example.Controller;

import org.example.Domain.User;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> payload) {
        try {
            String name = payload.get("name");
            String surname = payload.get("surname");
            String email = payload.get("email");
            String password = payload.get("password");
            String role = payload.getOrDefault("role", "CUSTOMER");

            // Generate staff number for librarians, null for customers
            String staffNumber = null;
            if ("LIBRARIAN".equals(role)) {
                staffNumber = "LIB" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            }

            User user = userService.signup(email, password, role, name, surname, staffNumber);

            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "user", Map.of(
                    "id", user.getId(),
                    "name", user.getName(),
                    "surname", user.getSurname(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole(),
                    "staffNumber", user.getStaffNumber() != null ? user.getStaffNumber() : ""
                )
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Signup failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");

            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email and password required"));
            }

            User user = userService.findByEmail(email);

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }

            return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole(),
                "staffNumber", user.getStaffNumber() != null ? user.getStaffNumber() : ""
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }
}
