package org.example.Service;

import org.example.Domain.Customer;
import org.example.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Find by username
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    // Add customer
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete customer by ID
    public boolean deleteCustomer(int id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
