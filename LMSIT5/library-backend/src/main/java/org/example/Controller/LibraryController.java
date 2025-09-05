package org.example.Controller;

import org.example.Domain.Library;
import org.example.Service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@CrossOrigin(origins = "http://localhost:5173")
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    @GetMapping
    public List<Library> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @PostMapping
    public Library createLibrary(@RequestBody Library library) {
        return libraryService.createLibrary(library);
    }

    @GetMapping("/{id}")
    public Library getLibrary(@PathVariable int id) {
        return libraryService.getLibraryById(id);
    }

    @PutMapping
    public Library updateLibrary(@RequestBody Library library) {
        return libraryService.updateLibrary(library);
    }

    @DeleteMapping("/{id}")
    public boolean deleteLibrary(@PathVariable int id) {
        return libraryService.deleteLibrary(id);
    }

    @GetMapping("/count")
    public long getLibraryCount() {
        return libraryService.getLibraryCount();
    }
}
