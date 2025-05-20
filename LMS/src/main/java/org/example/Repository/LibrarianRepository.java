package org.example.Repository;
/**
 * LibrarianRepository.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Librarian;

import java.util.HashMap;
import java.util.Map;

public class LibrarianRepository {
    private Map<Integer, Librarian> librarianMap = new HashMap<>();

    public Librarian save(Librarian librarian) {
        librarianMap.put(librarian.getId(), librarian);
        return librarian;
    }

    public Librarian findById(int id) {
        return librarianMap.get(id);
    }

    public Librarian update(Librarian librarian) {
        if (librarianMap.containsKey(librarian.getId())) {
            librarianMap.put(librarian.getId(), librarian);
            return librarian;
        }
        return null;
    }

    public boolean delete(int id) {
        if (librarianMap.containsKey(id)) {
            librarianMap.remove(id);
            return true;
        }
        return false;
    }

    public Map<Integer, Librarian> findAll() {
        return librarianMap;
    }
}