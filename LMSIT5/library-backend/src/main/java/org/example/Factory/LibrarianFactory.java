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
        return new Librarian(id, name);
    }
}