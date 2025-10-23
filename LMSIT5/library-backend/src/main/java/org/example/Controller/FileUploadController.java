package org.example.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:5173")
public class FileUploadController {

    // Directory where uploaded files will be stored
    private final String uploadDir = "uploads/";
    private final String baseUrl = "http://localhost:8080/api/files/";

    // Upload book cover image
    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadBookImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Please select a file to upload"));
            }

            // Check if file is an image
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Please upload an image file (JPG, PNG, GIF)"));
            }

            // Create upload directory if it doesn't exist
            File uploadDirFile = new File(uploadDir + "images/");
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            Path filePath = Paths.get(uploadDir + "images/" + uniqueFilename);
            Files.write(filePath, file.getBytes());

            // Return file URL
            String fileUrl = baseUrl + "images/" + uniqueFilename;
            return ResponseEntity.ok(Map.of(
                "message", "Image uploaded successfully",
                "imageUrl", fileUrl,
                "filename", uniqueFilename
            ));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload image: " + e.getMessage()));
        }
    }

    // Upload book PDF
    @PostMapping("/upload/pdf")
    public ResponseEntity<?> uploadBookPdf(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Please select a PDF file to upload"));
            }

            // Check if file is a PDF
            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Please upload a PDF file"));
            }

            // Create upload directory if it doesn't exist
            File uploadDirFile = new File(uploadDir + "pdfs/");
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + ".pdf";

            // Save file
            Path filePath = Paths.get(uploadDir + "pdfs/" + uniqueFilename);
            Files.write(filePath, file.getBytes());

            // Return file URL
            String fileUrl = baseUrl + "pdfs/" + uniqueFilename;
            return ResponseEntity.ok(Map.of(
                "message", "PDF uploaded successfully",
                "pdfUrl", fileUrl,
                "filename", uniqueFilename,
                "originalFilename", originalFilename
            ));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload PDF: " + e.getMessage()));
        }
    }

    // Serve uploaded files
    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir + "images/" + filename);
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Determine content type based on file extension
            String contentType = "image/jpeg"; // default
            if (filename.toLowerCase().endsWith(".png")) {
                contentType = "image/png";
            } else if (filename.toLowerCase().endsWith(".gif")) {
                contentType = "image/gif";
            }

            return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pdfs/{filename}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir + "pdfs/" + filename);
            byte[] fileBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
