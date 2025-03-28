package org.example.Domain;


public class Librarian {
    private int id;
    private String name;

    public Librarian(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addBook() {
        System.out.println("Book added");
    }

    public void searchBook(String bookTitle) {
        System.out.println("Searching book: " + bookTitle);
    }

    public void updateBook() {
        System.out.println("Updating book details...");
    }

    public void issueBook() {
        System.out.println("Issuing book...");
    }

    public void reserveBook() {
        System.out.println("Reserving book...");
    }

    public void returnBook() {
        System.out.println("Returning book...");
    }

    public void verifyMember(String username) {
        System.out.println("Verifying member: " + username);
    }

    public void logout() {
        System.out.println("Librarian logged out");
    }
}
