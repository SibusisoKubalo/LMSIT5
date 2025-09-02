package org.example.Factory;
/**
 * LibrarianFactory.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Librarian;

public class LibrarianFactory {
    public static Librarian createLibrarian(int id, String name) {
        if (id <= 0 || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid input for creating Librarian");
        }
        return new Librarian.Builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Librarian createLibrarianWithName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return new Librarian.Builder()
                .name(name)
                .build();
    }

    public static Librarian createEmptyLibrarian() {
        return new Librarian.Builder().build();
    }
}