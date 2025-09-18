package org.example.Controller;

import org.example.Service.BorrowTransactionService;
import org.example.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private BorrowTransactionService borrowTransactionService;
    @Autowired
    private BookService bookService;

    @GetMapping("/usage")
    public Map<String, Object> getUsageReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("totalBorrows", borrowTransactionService.getAllTransactions().size());
        // Add more analytics as needed
        return report;
    }

    @GetMapping("/inventory")
    public Map<String, Object> getInventoryReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("totalBooks", bookService.getAllBooks().size());
        // Add more inventory details as needed
        return report;
    }

    @GetMapping("/financial")
    public Map<String, Object> getFinancialReport() {
        Map<String, Object> report = new HashMap<>();
        double totalFines = borrowTransactionService.getAllTransactions().stream().mapToDouble(t -> t.getFine()).sum();
        report.put("totalFinesCollected", totalFines);
        return report;
    }
}

