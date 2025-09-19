package org.example.Factory;

import org.example.Domain.Customer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomerFactory {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static Customer createCustomer(String username, String password) {
        String hashedPassword = encoder.encode(password); // Hash the password
        Customer customer = new Customer(username, hashedPassword);
        // If needed, set role manually
        customer.setRole("CUSTOMER");
        return customer;
    }

}
