package org.example.Repository;

import org.example.Domain.LibraryDatabase;

public class LibraryDatabaseRepository {
    private final LibraryDatabase libraryDatabase = new LibraryDatabase();

    public LibraryDatabase getDatabase() {
        return libraryDatabase;
    }
}

