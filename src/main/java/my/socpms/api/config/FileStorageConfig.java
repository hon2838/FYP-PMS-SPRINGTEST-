// src/main/java/my/socpms/api/config/FileStorageConfig.java
package my.socpms.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageConfig {
    private String uploadDir;
    private long maxSize = 10485760; // 10MB
    private String[] allowedTypes = {".pdf", ".doc", ".docx"};
}