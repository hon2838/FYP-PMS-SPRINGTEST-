// src/main/java/my/socpms/api/controller/FileController.java
package my.socpms.api.controller;

import my.socpms.api.service.FileStorageService;
import my.socpms.api.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        return ResponseEntity.ok(new ApiResponse("success", "File uploaded successfully", fileName));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable String fileId) {
        return fileStorageService.loadFile(fileId);
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(@PathVariable String fileId) {
        fileStorageService.deleteFile(fileId);
        return ResponseEntity.ok(new ApiResponse("success", "File deleted successfully"));
    }
}