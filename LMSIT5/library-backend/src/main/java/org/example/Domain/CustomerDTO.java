package org.example.Domain;

public class CustomerDTO {
    private int customerId;
    private String username;
    private String role;

    public CustomerDTO(int customerId, String username, String role) {
        this.customerId = customerId;
        this.username = username;
        this.role = role;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}

