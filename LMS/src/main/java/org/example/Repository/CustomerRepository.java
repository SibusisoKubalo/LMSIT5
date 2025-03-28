package org.example.Repository;

import org.example.Domain.Customer;
import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {
    private final Map<String, Customer> customerMap = new HashMap<>();

    public void save(Customer customer) {
        customerMap.put(customer.getUsername(), customer);
    }

    public Customer findByUsername(String username) {
        return customerMap.get(username);
    }

    public void delete(String username) {
        customerMap.remove(username);
    }
}
