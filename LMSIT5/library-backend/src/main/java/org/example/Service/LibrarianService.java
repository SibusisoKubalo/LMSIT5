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

    public Librarian createLibrarian(Librarian librarian) {
        return repository.save(librarian);
    }

    public Librarian getLibrarianById(int id) {
        Optional<Librarian> librarian = repository.findById(id);
        return librarian.orElse(null);
    }

    public Librarian updateLibrarian(Librarian librarian) {
        return repository.save(librarian);
    }

    public boolean deleteLibrarian(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Librarian> getAllLibrarians() {
        return repository.findAll();
    }
}