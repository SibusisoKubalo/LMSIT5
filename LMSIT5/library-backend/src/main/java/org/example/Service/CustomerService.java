package org.example.Service;

import org.example.Domain.Customer;
import org.example.Repository.CustomerRepository;

public class CustomerService {
    private final CustomerRepository customerRepository = new CustomerRepository();

    public void registerCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findCustomer(String username) {
        return customerRepository.findByUsername(username);
    }

    public void deleteCustomer(String username) {
        customerRepository.delete(username);
    }
}