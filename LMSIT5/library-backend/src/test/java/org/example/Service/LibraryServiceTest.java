package org.example.Service;

import org.example.Domain.Library;
import org.example.Factory.LibraryFactory;
import org.example.Repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

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
        Library library = LibraryFactory.createLibrary("CPUT Library", "admin", "password123", 100, "admin@library.com");
        when(repository.save(library)).thenReturn(library);

        Library created = service.createLibrary(library);
        assertNotNull(created);
        assertEquals("CPUT Library", created.getName());
    }

    @Test
    public void testGetLibraryById() {
        Library library = LibraryFactory.createLibrary("CPUT Library", "admin", "password123", 100, "admin@library.com");
        when(repository.findById(1)).thenReturn(Optional.of(library));

        Library found = service.getLibraryById(1);
        assertNotNull(found);
        assertEquals("CPUT Library", found.getName());
    }

    @Test
    public void testUpdateLibrary() {
        Library library = LibraryFactory.createLibrary("Updated Library", "admin", "password123", 100, "admin@library.com");
        when(repository.save(library)).thenReturn(library);

        Library updated = service.updateLibrary(library);
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

    @Test
    public void testGetAllLibraries() {
        List<Library> libraries = List.of(
                LibraryFactory.createLibrary("Lib1", "user1", "pass1", 111, "a@b.com"),
                LibraryFactory.createLibrary("Lib2", "user2", "pass2", 222, "c@d.com")
        );

        when(repository.findAll()).thenReturn(libraries);

        List<Library> result = service.getAllLibraries();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetLibraryCount() {
        when(repository.count()).thenReturn(5L);
        long count = service.getLibraryCount();
        assertEquals(5, count);
    }
}
