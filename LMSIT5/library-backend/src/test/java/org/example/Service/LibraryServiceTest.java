package org.example.Service;
/**
 * LibraryServiceTest.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;
import org.example.Factory.LibraryFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {
    private LibraryService service = new LibraryService();

    @Test
    public void testCreateLibrary() {
        Library library = LibraryFactory.createLibrary(1, "CPUT Library", "admin", "password123", 100, "admin@library.com");
        Library created = service.createLibrary(library);
        assertNotNull(created);
        assertEquals("CPUT Library", created.getName());
    }

    @Test
    public void testGetLibraryById() {
        Library library = LibraryFactory.createLibrary(1, "CPUT Library", "admin", "password123", 100, "admin@library.com");
        service.createLibrary(library);
        Library found = service.getLibraryById(1);
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    @Test
    public void testUpdateLibrary() {
        Library library = LibraryFactory.createLibrary(1, "CPUT Library", "admin", "password123", 100, "admin@library.com");
        service.createLibrary(library);
        Library updatedLibrary = new Library(1, "Updated Library", "admin", "password123", 100, "admin@library.com");
        Library updated = service.updateLibrary(updatedLibrary);
        assertNotNull(updated);
        assertEquals("Updated Library", updated.getName());
    }

    @Test
    public void testDeleteLibrary() {
        Library library = LibraryFactory.createLibrary(1, "CPUT Library", "admin", "password123", 100, "admin@library.com");
        service.createLibrary(library);
        boolean deleted = service.deleteLibrary(1);
        assertTrue(deleted);
    }
}