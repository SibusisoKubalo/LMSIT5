package org.example.Controller;

import org.example.Service.BookService;
import org.example.Service.CustomerService;
import org.example.Service.LibrarianService;
import org.example.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
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
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("books", (long) bookService.getAllBooks().size());
        stats.put("customers", (long) customerService.getAllCustomers().size());
        stats.put("librarians", (long) librarianService.getAllLibrarians().size());
        stats.put("notifications", notificationService.getNotificationCount());
        return stats;
    }
}