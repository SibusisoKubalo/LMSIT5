package org.example.Service;

import org.example.Domain.Customer;
import org.example.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public boolean registerCustomer(Customer customer) {
        return customerRepository.save(customer) != null;
    }

    public Customer findCustomer(String username) {
        Optional<Customer> customer = customerRepository.findById(username);
        return customer.orElse(null);
    }

    public boolean deleteCustomer(String username) {
        if (customerRepository.existsById(username)) {
            customerRepository.deleteById(username);
            return true;
        }
        return false;
    }
}