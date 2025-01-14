package my.socpms.api.exception;

public class FileTooLargeException extends RuntimeException {
    public FileTooLargeException() {
        super("File size exceeds maximum limit of 10MB");
    }
}