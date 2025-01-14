// src/main/java/my/socpms/api/dto/AuthResponse.java
package my.socpms.api.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}