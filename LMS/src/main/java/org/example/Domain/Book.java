package org.example.Domain;

//Mika'il Vallie 230259200
// Log: BookUpdateBuilder

public class Book {
    private String title, subject , author;
    private int bookId;

    private Book(){
    }

    private Book(Builder builder) {
        this.title = builder.title
        this.bookId = builder.bookId;
        this.subject = builder.subject;
        this.author = builder.author;
    }

    @Override
    public String toString() {
        return "Builder{" +
                "title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", author='" + author + '\'' +
                ", bookId=" + bookId +
                '}';
    }

    public static class Builder{
        private String title, subject, author;
        private int bookId;

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder subject(String subject){
            this.subject = subject;
            return this;
        }

        public Builder author(String author){
            this.author = author;
            return this;
        }

        public Builder bookId(int bookId){
            this.bookId = bookId;
            return this;
        }

        public Book build(){
            return new Book(this);
        }


    }




}

