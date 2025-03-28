package org.example.Service;

import org.example.Domain.Librarian;
import org.example.Repository.LibrarianRepository;

public class LibrarianService {
    private final LibrarianRepository librarianRepository = new LibrarianRepository();

    public void addLibrarian(Librarian librarian) {
        librarianRepository.save(librarian);
    }

    public Librarian findLibrarian(int id) {
        return librarianRepository.findById(id);
    }

    public void deleteLibrarian(int id) {
        librarianRepository.delete(id);
    }
}

