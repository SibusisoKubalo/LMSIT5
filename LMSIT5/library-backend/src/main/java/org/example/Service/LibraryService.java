package org.example.Service;
/**
 * LibraryService.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;
import org.example.Repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final LibraryRepository repository;

    @Autowired
    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

    public Library createLibrary(Library library) {
        return repository.save(library);
    }

    public Library getLibraryById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Library updateLibrary(Library library) {
        return repository.save(library);
    }

    public boolean deleteLibrary(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Library> getAllLibraries() {
        return repository.findAll();
    }
}