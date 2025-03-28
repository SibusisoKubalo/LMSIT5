package org.example.Factory;


import org.example.Domain.LibraryDatabase;

public class LibraryDatabaseFactory {
    public static LibraryDatabase createLibraryDatabase() {
        return new LibraryDatabase();
    }
}

