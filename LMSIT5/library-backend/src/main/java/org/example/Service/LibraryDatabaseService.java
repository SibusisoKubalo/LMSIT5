package org.example.Service;

import org.example.Domain.LibraryDatabase;
import org.example.Repository.LibraryDatabaseRepository;

public class LibraryDatabaseService {
    private final LibraryDatabaseRepository repository = new LibraryDatabaseRepository();

    public LibraryDatabase getDatabase() {
        return repository.getDatabase();
    }
}

