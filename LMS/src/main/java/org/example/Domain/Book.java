package org.example.Domain;


public class Book {
    private String title;
    private int bookId;
    private String subject;
    private String author;

    public Book(String title, int bookId, String subject, String author) {
        this.title = title;
        this.bookId = bookId;
        this.subject = subject;
        this.author = author;
    }



    public void displayBook() {
        System.out.println("Book: " + title + " by " + author);
    }

    public void updateBookStatus() {
        System.out.println("Updating book status...");
    }

}

