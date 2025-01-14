// src/main/java/my/socpms/api/dto/LoginRequest.java
package my.socpms.api.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}