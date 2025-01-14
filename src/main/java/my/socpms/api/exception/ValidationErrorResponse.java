package my.socpms.api.exception;

import java.util.List;
import lombok.Data;

@Data
public class ValidationErrorResponse {
    private List<String> errors;

    public ValidationErrorResponse(List<String> errors) {
        this.errors = errors;
    }
}