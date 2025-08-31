package org.example.Service;
/**
 * LibrarianRepository.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Librarian;
import org.example.Repository.LibrarianRepository;

import java.util.Map;

public class LibrarianService {
    private LibrarianRepository repository = new LibrarianRepository();

    public Librarian createLibrarian(Librarian librarian) {
        return repository.save(librarian);
    }

    public Librarian getLibrarianById(int id) {
        return repository.findById(id);
    }

    public Librarian updateLibrarian(Librarian librarian) {
        return repository.update(librarian);
    }

    public boolean deleteLibrarian(int id) {
        return repository.delete(id);
    }

    public Map<Integer, Librarian> getAllLibrarians() {
        return repository.findAll();
    }
}