package org.example.Factory;

//Ayrton Williams 220086168

import org.example.Domain.LibraryDatabase;
import org.example.Domain.Book;
import java.util.List;

public class LibraryDatabaseFactory {
    public static LibraryDatabase createLibraryDatabase(List<Book> books) {
        LibraryDatabase.Builder builder = new LibraryDatabase.Builder();
        for (Book book : books) {
            builder.addBook(book);
        }
        return builder.build();
    }
}