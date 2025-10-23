package org.example.Controller;

import org.example.Domain.User;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    @Autowired
    private UserService userService;

    // Get all customers (users with CUSTOMER role)
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllCustomers() {
        try {
            // Get all users with CUSTOMER role
            List<User> allUsers = userService.getAllUsers();
            List<Map<String, Object>> customers = allUsers.stream()
                .filter(u -> "CUSTOMER".equals(u.getRole()))
                .map(u -> {
                    Map<String, Object> customerMap = new HashMap<>();
                    customerMap.put("customerId", u.getId());
                    customerMap.put("username", u.getUsername());
                    customerMap.put("email", u.getEmail());
                    customerMap.put("name", u.getName());
                    customerMap.put("surname", u.getSurname());
                    customerMap.put("role", u.getRole());
                    return customerMap;
                })
                .collect(Collectors.toList());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }

    // Add/register a new customer (create user with CUSTOMER role)
    @PostMapping("/register")
    public ResponseEntity<?> addCustomer(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String password = payload.get("password");
            String email = payload.get("email");
            String name = payload.get("name");
            String surname = payload.get("surname");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username and password required"));
            }

            // Set default values if not provided
            if (email == null) email = username + "@library.com";
            if (name == null) name = username;
            if (surname == null) surname = "User";

            // Create user with CUSTOMER role
            User user = userService.signup(email, password, "CUSTOMER", name, surname, null);

            return ResponseEntity.ok(Map.of(
                "customerId", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "role", user.getRole(),
                "message", "Customer added successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to add customer: " + e.getMessage()));
        }
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            boolean success = userService.deleteUser(id);
            if (!success) {
                return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
            }
            return ResponseEntity.ok(Map.of("message", "Customer deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete customer: " + e.getMessage()));
        }
    }

    // Get customer by username
    @GetMapping("/{username}")
    public ResponseEntity<?> getCustomerByUsername(@PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            if (!"CUSTOMER".equals(user.getRole())) {
                return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
            }

            return ResponseEntity.ok(Map.of(
                "customerId", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "role", user.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
        }
    }
}