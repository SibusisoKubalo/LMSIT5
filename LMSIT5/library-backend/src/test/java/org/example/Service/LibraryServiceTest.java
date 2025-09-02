package org.example.Service;
/**
 * LibraryServiceTest.java
 *
 * @author Sibusiso Kubalo
 * Student Num: 218316038
 */

import org.example.Domain.Library;
import org.example.Factory.LibraryFactory;
import org.example.Repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {
    private LibraryRepository repository;
    private LibraryService service;

    @BeforeEach
    public void setUp() {
        repository = mock(LibraryRepository.class);
        service = new LibraryService(repository);
    }

    @Test
    public void testCreateLibrary() {
        Library library = LibraryFactory.createLibrary(1, "CPUT Library", "admin", "password123", 100, "admin@library.com");
        when(repository.save(library)).thenReturn(library);

        Library created = service.createLibrary(library);
        assertNotNull(created);
        assertEquals("CPUT Library", created.getName());
    }

    @Test
    public void testGetLibraryById() {
        Library library = LibraryFactory.createLibrary(1, "CPUT Library", "admin", "password123", 100, "admin@library.com");
        when(repository.findById(1)).thenReturn(Optional.of(library));

        Library found = service.getLibraryById(1);
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    @Test
    public void testUpdateLibrary() {
        Library updatedLibrary = new Library(1, "Updated Library", "admin", "password123", 100, "admin@library.com");
        when(repository.save(updatedLibrary)).thenReturn(updatedLibrary);

        Library updated = service.updateLibrary(updatedLibrary);
        assertNotNull(updated);
        assertEquals("Updated Library", updated.getName());
    }

    @Test
    public void testDeleteLibrary() {
        when(repository.existsById(1)).thenReturn(true);

        boolean deleted = service.deleteLibrary(1);
        assertTrue(deleted);
        verify(repository).deleteById(1);
    }
}