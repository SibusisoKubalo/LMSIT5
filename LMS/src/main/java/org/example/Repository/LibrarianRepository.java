package org.example.Repository;

import org.example.Domain.Librarian;
import java.util.HashMap;
import java.util.Map;

public class LibrarianRepository {
    private final Map<Integer, Librarian> librarianMap = new HashMap<>();

    public void save(Librarian librarian) {
        librarianMap.put(librarian.getId(), librarian);
    }

    public Librarian findById(int id) {
        return librarianMap.get(id);
    }

    public void delete(int id) {
        librarianMap.remove(id);
    }
}
