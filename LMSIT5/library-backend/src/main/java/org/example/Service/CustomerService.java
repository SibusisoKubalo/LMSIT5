package org.example.Service;

import org.example.Domain.Customer;
import org.example.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public boolean registerCustomer(Customer customer) {
        return customerRepository.save(customer) != null;
    }

    public Customer findCustomer(int customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.orElse(null);
    }

    public boolean deleteCustomer(int customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    // ===== Dashboard statistics =====
    public long getCustomerCount() {
        return customerRepository.count();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}