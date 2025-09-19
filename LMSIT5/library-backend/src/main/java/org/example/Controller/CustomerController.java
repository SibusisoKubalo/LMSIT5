package org.example.Controller;

import org.example.Domain.Customer;
import org.example.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Add/register a new customer
    @PostMapping("/register")
    public ResponseEntity<?> addCustomer(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password required"));
        }

        // Wrap into a Customer object
        Customer customer = new Customer(username, password);
        Customer savedCustomer = customerService.addCustomer(customer);

        return ResponseEntity.ok(savedCustomer);
    }

    // Delete a customer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        boolean success = customerService.deleteCustomer(id);
        if (!success) {
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
        }
        return ResponseEntity.ok(Map.of("message", "Customer deleted successfully"));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getCustomerByUsername(@PathVariable String username) {
        Optional<Customer> customerOpt = customerService.findByUsername(username);
        if (customerOpt.isPresent()) {
            return ResponseEntity.ok(customerOpt.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
        }
    }
}