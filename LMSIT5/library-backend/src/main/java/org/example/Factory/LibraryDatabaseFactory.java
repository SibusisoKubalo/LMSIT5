package org.example.Factory;

//Ayrton Williams 220086168

import org.example.Domain.LibraryDatabase;
import org.example.Domain.Book;

public class LibraryDatabaseFactory {
    public static LibraryDatabase createLibraryDatabase(List<Book> books) {
        return new LibraryDatabase().Builder()
                .Book(books)
                .build();
    }
}
