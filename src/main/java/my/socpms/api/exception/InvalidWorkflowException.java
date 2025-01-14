// src/main/java/my/socpms/api/exception/InvalidWorkflowException.java
package my.socpms.api.exception;

public class InvalidWorkflowException extends RuntimeException {
    public InvalidWorkflowException() {
        super("Invalid workflow transition");
    }
    
    public InvalidWorkflowException(String message) {
        super(message);
    }
}