package my.socpms.api.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private String status;
    private String message;
    private Object data;
    private Object[] errors;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}