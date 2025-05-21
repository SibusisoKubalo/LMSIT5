package org.example.Repository;
/**
 * LibraryRepository.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;

import java.util.HashMap;
import java.util.Map;

public class LibraryRepository {
    private Map<Integer, Library> libraryMap = new HashMap<>();

    public Library save(Library library) {
        libraryMap.put(library.getId(), library);
        return library;
    }

    public Library findById(int id) {
        return libraryMap.get(id);
    }

    public Library update(Library library) {
        if (libraryMap.containsKey(library.getId())) {
            libraryMap.put(library.getId(), library);
            return library;
        }
        return null;
    }

    public boolean delete(int id) {
        if (libraryMap.containsKey(id)) {
            libraryMap.remove(id);
            return true;
        }
        return false;
    }

    public Map<Integer, Library> findAll() {
        return libraryMap;
    }
}