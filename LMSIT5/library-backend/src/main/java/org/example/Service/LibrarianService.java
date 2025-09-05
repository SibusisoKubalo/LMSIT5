package org.example.Service;
/**
 * LibrarianRepository.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Librarian;
import org.example.Repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibrarianService {

    @Autowired
    private LibrarianRepository repository;

    // Create a new librarian
    public Librarian createLibrarian(Librarian librarian) {
        return repository.save(librarian);
    }

    // Get librarian by ID
    public Librarian getLibrarianById(int id) {
        Optional<Librarian> librarian = repository.findById(id);
        return librarian.orElse(null);
    }

    // Update existing librarian
    public Librarian updateLibrarian(Librarian librarian) {
        // Optional: check if exists first
        if (repository.existsById(librarian.getId())) {
            return repository.save(librarian);
        }
        return null;
    }

    // Delete librarian by ID
    public boolean deleteLibrarian(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get all librarians
    public List<Librarian> getAllLibrarians() {
        return repository.findAll();
    }

    // Count of librarians
    public long getLibrarianCount() {
        return repository.count();
    }
}
