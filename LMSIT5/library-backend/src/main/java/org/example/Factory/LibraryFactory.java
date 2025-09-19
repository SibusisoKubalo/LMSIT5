package org.example.Factory;

import org.example.Domain.Library;

public class LibraryFactory {
    public static Library createLibrary(String name, String username, String password, int num, String email) {
        return new Library(name, username, password, num, email);
    }
}
