package org.example.Repository;

import org.example.Domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // You can add custom query methods if needed, e.g.:
    // Optional<Customer> findByUsername(String username);
}
