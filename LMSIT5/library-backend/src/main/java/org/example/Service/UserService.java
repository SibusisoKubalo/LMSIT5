package org.example.Service;

import org.example.Domain.User;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = new User(name, surname, email, passwordEncoder.encode(password), role, staffNumber);
        return userRepository.save(user);
    }

    // ✅ Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
