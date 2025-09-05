package org.example.Controller;

import org.example.Domain.Librarian;
import org.example.Service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/librarians")
@CrossOrigin(origins = "http://localhost:5173")
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;

    // Get all
    @GetMapping
    public List<Librarian> getAllLibrarians() {
        return librarianService.getAllLibrarians();
    }

    // ✅ Create new
    @PostMapping
    public Librarian createLibrarian(@RequestBody Librarian librarian) {
        return librarianService.createLibrarian(librarian);
    }

    // ✅ Update existing
    @PutMapping
    public Librarian updateLibrarian(@RequestBody Librarian librarian) {
        return librarianService.updateLibrarian(librarian);
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public boolean deleteLibrarian(@PathVariable int id) {
        return librarianService.deleteLibrarian(id);
    }
}
