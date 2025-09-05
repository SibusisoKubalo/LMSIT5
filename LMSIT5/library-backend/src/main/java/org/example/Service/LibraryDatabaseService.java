
package org.example.Service;

import org.example.Domain.LibraryDatabase;
import org.example.Repository.LibraryDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryDatabaseService {
    private final LibraryDatabaseRepository repository;

    @Autowired
    public LibraryDatabaseService(LibraryDatabaseRepository repository) {
        this.repository = repository;
    }

    public LibraryDatabase getDatabase(int id) {
        return repository.findById(id).orElse(null);
    }

    public LibraryDatabase saveDatabase(LibraryDatabase database) {
        return repository.save(database);
    }

    public boolean deleteDatabase(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }


}