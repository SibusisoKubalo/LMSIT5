package org.example.Domain;

// Mika'il Vallie 230259200
// Log:CustomerUpdate
//    :AddBuilderCustomer

public class Customer {
    private String username;
    private String password;

    private Customer(){
    }

    public Customer(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
        private String username;
        private String password;

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
