
package org.example.Service;

import org.example.Domain.Customer;
import org.example.Repository.CustomerRepository;

public class CustomerService {
    private final CustomerRepository customerRepository = new CustomerRepository();

    public boolean registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findCustomer(String username) {
        return customerRepository.findByUsername(username);
    }

    public boolean deleteCustomer(String username) {
        return customerRepository.delete(username);
    }
}