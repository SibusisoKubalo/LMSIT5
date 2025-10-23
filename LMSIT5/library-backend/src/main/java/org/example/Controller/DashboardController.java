package org.example.Controller;

import org.example.Service.BookService;
import org.example.Service.CustomerService;
import org.example.Service.LibrarianService;
import org.example.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        try {
            stats.put("books", (long) bookService.getAllBooks().size());
        } catch (Exception e) {
            stats.put("books", 0L);
        }
        try {
            stats.put("customers", (long) customerService.getAllCustomers().size());
        } catch (Exception e) {
            stats.put("customers", 0L);
        }
        try {
            stats.put("librarians", (long) librarianService.getAllLibrarians().size());
        } catch (Exception e) {
            stats.put("librarians", 0L);
        }
        try {
            stats.put("notifications", notificationService.getNotificationCount());
        } catch (Exception e) {
            stats.put("notifications", 0L);
        }
        return stats;
    }
}