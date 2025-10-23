package org.example.Service;

import org.example.Domain.User;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Signup
    public User signup(String email, String password, String role, String name, String surname, String staffNumber) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // Generate username from email (part before @)
        String username = email.split("@")[0];

        // Check if username already exists, if so, append a number
        int counter = 1;
        String originalUsername = username;
        while (userRepository.findByUsername(username).isPresent()) {
            username = originalUsername + counter;
            counter++;
        }

        User user = new User(name, surname, username, email, passwordEncoder.encode(password), role, staffNumber);
        return userRepository.save(user);
    }

    // ✅ Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Delete user by ID
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
