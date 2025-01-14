package my.socpms.api.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import my.socpms.api.exception.FileTooLargeException;
import my.socpms.api.exception.InvalidFileTypeException;

@Service
public class FileValidationService {
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_TYPES = Arrays.asList(
        "application/pdf", 
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    public void validateFile(MultipartFile file) throws FileTooLargeException, InvalidFileTypeException {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException();
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new InvalidFileTypeException();
        }
    }
}