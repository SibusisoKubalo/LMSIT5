package org.example.Service;

import org.example.Domain.Library;
import org.example.Repository.LibraryRepository;

public class LibraryService {
    private final LibraryRepository libraryRepository = new LibraryRepository();

    public void createLibrary(Library library) {
        libraryRepository.save(library);
    }

    public Library findLibrary(int id) {
        return libraryRepository.findById(id);
    }

    public void deleteLibrary(int id) {
        libraryRepository.delete(id);
    }
}
