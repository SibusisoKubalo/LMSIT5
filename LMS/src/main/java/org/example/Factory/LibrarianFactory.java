package org.example.Factory;


import org.example.Domain.Librarian;

public class LibrarianFactory {
    public static Librarian createLibrarian(int id, String name) {
        return new Librarian(id, name);
    }
}
