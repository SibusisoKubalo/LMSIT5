package org.example.Factory;


import org.example.Domain.Customer;

public class CustomerFactory {
    public static Customer createCustomer(String username, String password) {
        return new Customer(username, password);
    }
}
