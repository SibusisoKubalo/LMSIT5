package org.example.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Domain.User;
import org.example.Repository.UserRepository;
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

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletRequest request) {
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

        // Create session
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        return ResponseEntity.ok(Map.of(
                "email", user.getEmail(),
                "role", user.getRole()
        ));
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

    // GET LOGGED-IN USER
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return ResponseEntity.ok().body(null);

        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.ok().body(null);

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
