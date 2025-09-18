package org.example.Domain;

// Mika'il Vallie 230259200
// Log:CustomerUpdate
//    :AddBuilderCustomer
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String username;
    private String password;

    @OneToMany(mappedBy = "customer")
    private List<BorrowTransaction> borrowHistory;
    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;

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
    public List<BorrowTransaction> getBorrowHistory() { return borrowHistory; }
    public void setBorrowHistory(List<BorrowTransaction> borrowHistory) { this.borrowHistory = borrowHistory; }
    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", borrowHistory=" + (borrowHistory != null ? borrowHistory.size() : 0) +
                ", reservations=" + (reservations != null ? reservations.size() : 0) +
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