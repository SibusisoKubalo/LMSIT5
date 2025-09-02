package org.example.Factory;
/**
 * LibraryFactory.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;

public class LibraryFactory {
    public static Library createLibrary(int id, String name) {
        return new Library.Builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Library createLibraryWithName(String name) {
        return new Library.Builder()
                .name(name)
                .build();
    }

    public static Library createEmptyLibrary() {
        return new Library.Builder().build();
    }
}