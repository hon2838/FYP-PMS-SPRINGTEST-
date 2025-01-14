// src/main/java/my/socpms/api/controller/AuthController.java
package my.socpms.api.controller;

// Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import my.socpms.api.dto.ApiResponse;
import my.socpms.api.dto.AuthResponse;
import my.socpms.api.dto.LoginRequest;
import my.socpms.api.model.User;
import my.socpms.api.service.JWTService;
import my.socpms.api.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                .body(new ApiResponse("error", "No token provided"));
        }

        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            
            if (username != null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails)) {
                    return ResponseEntity.ok(new ApiResponse("success", "Token is valid"));
                }
            }
            
            return ResponseEntity.status(401)
                .body(new ApiResponse("error", "Invalid token"));
                
        } catch (Exception e) {
            return ResponseEntity.status(401)
                .body(new ApiResponse("error", "Token validation failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), 
                    loginRequest.getPassword()
                )
            );
            
            String accessToken = jwtService.generateToken(authentication.getName());
            String refreshToken = jwtService.generateRefreshToken(authentication.getName());
            
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken(accessToken);
            authResponse.setRefreshToken(refreshToken);
            
            return ResponseEntity.ok(new ApiResponse("success", "Login successful", authResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401)
                .body(new ApiResponse("error", "Invalid credentials"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        String token = refreshToken.substring(7);
        String email = jwtService.extractUsername(token);
        
        if (jwtService.isTokenValid(token, userService.loadUserByUsername(email))) {
            String newAccessToken = jwtService.generateToken(email);
            
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken(newAccessToken);
            authResponse.setRefreshToken(refreshToken);
            
            return ResponseEntity.ok(new ApiResponse("success", "Token refreshed", authResponse));
        }
        
        return ResponseEntity.badRequest().body(new ApiResponse("error", "Invalid refresh token"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(new ApiResponse("success", "User registered successfully", newUser));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        userService.initiatePasswordReset(email);
        return ResponseEntity.ok(new ApiResponse("success", "Password reset email sent"));
    }
}