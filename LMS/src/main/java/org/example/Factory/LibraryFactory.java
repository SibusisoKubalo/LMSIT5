package org.example.Factory;
/**
 * LibraryFactory.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;

public class LibraryFactory {
    public static Library createLibrary(int id, String name, String username, String password, int num, String email) {
        if (id <= 0 || name == null || name.isEmpty() || username == null || username.isEmpty() ||
                password == null || password.isEmpty() || email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid input for creating Library");
        }
        return new Library(id, name, username, password, num, email);
    }
}