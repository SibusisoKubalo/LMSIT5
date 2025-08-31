package org.example.Factory;


import org.example.Domain.Customer;

//Mika'il Vallie 230259200
// Log:BookFactory

public class CustomerFactory {

    public static Customer createCustomer(String username, String password) {
        return new Customer.Builder()
                .username(username)
                .password(password)
                .build();
    }


    public static Customer createCustomerWithUsername(String username) {
        return new Customer.Builder()
                .username(username)
                .build();
    }

    public static Customer createEmptyCustomer() {
        return new Customer.Builder().build();
    }
}
