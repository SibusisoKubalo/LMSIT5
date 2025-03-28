package org.example.Repository;


import org.example.Domain.Library;
import java.util.HashMap;
import java.util.Map;

public class LibraryRepository {
    private final Map<Integer, Library> libraryMap = new HashMap<>();

    public void save(Library library) {
        libraryMap.put(library.getId(), library);
    }

    public Library findById(int id) {
        return libraryMap.get(id);
    }

    public void delete(int id) {
        libraryMap.remove(id);
    }
}
