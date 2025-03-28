package org.example.Domain;


public class Customer {
    private String username;
    private String password;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void checkAccount() {
        System.out.println("Checking account details for " + username);
    }

    public void searchBook(String bookTitle) {
        System.out.println("Searching for book: " + bookTitle);
    }

    public void issueBook() {
        System.out.println("Issuing book...");
    }

    public void returnBook() {
        System.out.println("Returning book...");
    }

    public void reserveBook() {
        System.out.println("Reserving book...");
    }

    public void logout() {
        System.out.println("Customer logged out");
    }
}
