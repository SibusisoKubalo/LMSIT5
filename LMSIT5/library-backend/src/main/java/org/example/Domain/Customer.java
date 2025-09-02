package org.example.Domain;

// Mika'il Vallie 230259200
// Log:CustomerUpdate
//    :AddBuilderCustomer
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String username;
    private String password;

    private Customer() {}

    public Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.username = builder.username;
        this.password = builder.password;
    }

    // Getters and setters for JPA
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
        private int customerId;
        private String username;
        private String password;

        public Builder customerId(int customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}