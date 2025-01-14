package my.socpms.api.exception;

public class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException() {
        super("Invalid file type. Only PDF and Word documents are allowed");
    }
}