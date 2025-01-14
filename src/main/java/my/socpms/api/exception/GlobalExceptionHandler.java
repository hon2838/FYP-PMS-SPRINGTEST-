package my.socpms.api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import my.socpms.api.dto.ApiResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        if (e instanceof MethodArgumentNotValidException validationException) {
            List<String> errors = validationException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
                
            log.debug("Validation failed: {}", errors);
            return ResponseEntity.badRequest()
                .body(new ApiResponse("error", "Validation failed", errors));
        }
        
        if (e instanceof ConstraintViolationException violationException) {
            List<String> errors = violationException.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .toList();
                
            log.debug("Constraint violation: {}", errors);
            return ResponseEntity.badRequest()
                .body(new ApiResponse("error", "Validation failed", errors));
        }
        
        if (e instanceof UnauthorizedException) {
            log.warn("Unauthorized access attempt: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("error", e.getMessage()));
        }
        
        if (e instanceof ResourceNotFoundException) {
            log.debug("Resource not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("error", e.getMessage()));
        }

        log.error("Internal server error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ApiResponse("error", "Internal server error"));
    }
}