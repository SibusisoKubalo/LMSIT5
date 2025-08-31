package org.example.Service;
/**
 * LibraryService.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;
import org.example.Repository.LibraryRepository;

import java.util.Map;

public class LibraryService {
    private LibraryRepository repository = new LibraryRepository();

    public Library createLibrary(Library library) {
        return repository.save(library);
    }

    public Library getLibraryById(int id) {
        return repository.findById(id);
    }

    public Library updateLibrary(Library library) {
        return repository.update(library);
    }

    public boolean deleteLibrary(int id) {
        return repository.delete(id);
    }

    public Map<Integer, Library> getAllLibraries() {
        return repository.findAll();
    }
}