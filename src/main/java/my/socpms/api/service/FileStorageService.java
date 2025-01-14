package my.socpms.api.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import my.socpms.api.config.FileStorageConfig;

@Service
public class FileStorageService {
    
    private final Path fileStorageLocation;
    private final long maxFileSize;
    private final String[] allowedFileTypes;
    
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
            .toAbsolutePath().normalize();
        this.maxFileSize = fileStorageConfig.getMaxSize();
        this.allowedFileTypes = fileStorageConfig.getAllowedTypes();
        
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public String storeFile(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Invalid file: filename cannot be null");
        }

        // Validate file size
        if (file.getSize() > maxFileSize) {
            throw new RuntimeException("File size exceeds maximum limit of " + maxFileSize + " bytes");
        }
        
        String originalFilename = file.getOriginalFilename();
        // Validate file type
        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        if (fileExtension == null) {
            throw new IllegalArgumentException("File must have an extension");
        }

        boolean isValidFileType = false;
        for (String allowedType : allowedFileTypes) {
            if (allowedType.equalsIgnoreCase("." + fileExtension)) {
                isValidFileType = true;
                break;
            }
        }
        if (!isValidFileType) {
            throw new RuntimeException("File type not allowed. Supported types: " + String.join(", ", allowedFileTypes));
        }
        
        // Generate unique filename
        if (originalFilename == null) {
            throw new IllegalArgumentException("Original filename cannot be null");
        }
        String uniqueFileName = UUID.randomUUID().toString() + "-" + 
            StringUtils.cleanPath(originalFilename);
        
        try {
            // Copy file to storage location
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            return uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + uniqueFileName + ". Please try again!", ex);
        }
    }
    
    public ResponseEntity<?> loadFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                String contentType = determineContentType(fileName);
                
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (RuntimeException | MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
    
    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + fileName);
            }
            
            // Check if file is within upload directory to prevent directory traversal
            if (!filePath.toAbsolutePath().startsWith(this.fileStorageLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot delete file outside upload directory");
            }
            
            Files.delete(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file " + fileName, ex);
        }
    }
    
    private String determineContentType(String fileName) {
        String fileExtension = StringUtils.getFilenameExtension(fileName);
        if (fileExtension == null) {
            return "application/octet-stream";
        }
        
        return switch (fileExtension.toLowerCase()) {
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default -> "application/octet-stream";
        };
    }
}