package org.example.Service;
/**
 * LibrarianRepository.java
 *
 * Author: Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Librarian;
import org.example.Factory.LibrarianFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianServiceTest {
    private LibrarianService service = new LibrarianService();

    @Test
    public void testCreateLibrarian() {
        Librarian librarian = LibrarianFactory.createLibrarian(1, "Sibusiso Kubalo");
        Librarian created = service.createLibrarian(librarian);
        assertNotNull(created);
        assertEquals("Sibusiso Kubalo", created);
    }

    @Test
    public void testGetLibrarianById() {
        Librarian librarian = LibrarianFactory.createLibrarian(1, "Sibusiso Kubalo");
        service.createLibrarian(librarian);
        Librarian found = service.getLibrarianById(1);
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    @Test
    public void testUpdateLibrarian() {
        Librarian librarian = LibrarianFactory.createLibrarian(1, "Sibusiso Kubalo");
        service.createLibrarian(librarian);
        Librarian updatedLibrarian = new Librarian(1, "Amahle Kubalo");
        Librarian updated = service.updateLibrarian(updatedLibrarian);
        assertNotNull(updated);
        assertEquals("Amahle Kubalo", updated);
    }

    @Test
    public void testDeleteLibrarian() {
        Librarian librarian = LibrarianFactory.createLibrarian(1, "Sibusiso Kubalo");
        service.createLibrarian(librarian);
        boolean deleted = service.deleteLibrarian(1);
        assertTrue(deleted);
    }
}