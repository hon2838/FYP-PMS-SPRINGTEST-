# SOC Paperwork Management System - API Documentation

## Overview
The SOC Paperwork Management System is a web application that manages academic paperwork submissions and approvals through a hierarchical workflow system.

## Core Entities

### 1. User
```java
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String userType; // admin, staff, hod, dean
    private String department;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
```

### 2. Paperwork
```java
public class Paperwork {
    private Long id;
    private String refNumber;
    private String projectName;
    private String ppwType;
    private String session;
    private String background;
    private String aim;
    private LocalDate projectDate;
    private String documentPath;
    private String currentStage; // submitted, hod_review, dean_review, approved
    private Boolean hodApproval;
    private String hodNote;
    private LocalDateTime hodApprovalDate;
    private Boolean deanApproval;
    private String deanNote; 
    private LocalDateTime deanApprovalDate;
    private LocalDateTime submissionTime;
    private String userEmail;
    private Long userId;
    private Integer status;  // 0=pending, 1=approved
}
```

## API Endpoints

### Authentication
```
POST /api/auth/login
POST /api/auth/register
POST /api/auth/reset-password
```

### User Management 
```
GET    /api/users
POST   /api/users
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Paperwork Management
```
GET    /api/paperworks
POST   /api/paperworks
GET    /api/paperworks/{id}
PUT    /api/paperworks/{id}
DELETE /api/paperworks/{id}
POST   /api/paperworks/{id}/approve
POST   /api/paperworks/{id}/reject
GET    /api/paperworks/department/{deptId}
```

### File Management
```
POST   /api/files/upload
GET    /api/files/{fileId}
DELETE /api/files/{fileId}
```

### Audit Logs
```
GET    /api/audit-logs
GET    /api/audit-logs/{id}
GET    /api/audit-logs/user/{userId}
GET    /api/audit-logs/paperwork/{ppwId}
```

## Business Rules

### 1. Permission System
- Implement role-based access control (RBAC)
- Core permissions:
  - create_submission
  - edit_submission
  - delete_submission
  - view_submissions
  - approve_submissions
  - manage_users
  - generate_reports

### 2. Workflow Rules
```java
public enum PaperworkStage {
    SUBMITTED,
    HOD_REVIEW,
    DEAN_REVIEW,
    APPROVED,
    REJECTED
}
```

- Staff can create and edit their own paperwork
- HOD can review department paperwork
- Dean can review HOD-approved paperwork
- Admin has full access

### 3. Document Handling
- Support PDF, DOC, DOCX formats
- Maximum file size: 10MB
- Store files securely with unique names
- Maintain audit trail of document changes

### 4. Validation Rules
- Reference numbers must be unique
- Required fields: refNumber, projectName, ppwType, session
- Email validation for users
- Department required for staff/HOD
- Password complexity requirements

### 5. Notifications
```java
public interface NotificationService {
    void sendApprovalNotification(Paperwork paperwork);
    void sendRejectionNotification(Paperwork paperwork);
    void sendPasswordResetNotification(User user, String newPassword);
    void sendSystemErrorNotification(String title, String message);
}
```

## Security Requirements

1. Authentication:
- JWT-based authentication
- Token expiration
- Refresh token mechanism

2. Data Protection:
- Encrypt sensitive data
- Hash passwords with BCrypt
- Input validation and sanitization
- XSS protection
- CSRF protection

3. Rate Limiting:
```java
@RateLimiter(name = "paperwork-submission")
public class PaperworkSubmissionService {
    // Max 5 submissions per 5 minutes per user
}
```

## Auditing
Track all actions:
```java
public class AuditLog {
    private Long id;
    private Long userId;
    private String action;
    private String details;
    private String ipAddress;
    private LocalDateTime timestamp;
    private Long ppwId;
}
```

## Database Schema
Use similar structure as the PHP version but with proper foreign key constraints and indexes:

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50) NOT NULL,
    department VARCHAR(255),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

CREATE TABLE paperworks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ref_number VARCHAR(255) UNIQUE NOT NULL,
    project_name VARCHAR(255) NOT NULL,
    ppw_type VARCHAR(100) NOT NULL,
    session VARCHAR(50) NOT NULL,
    current_stage VARCHAR(50) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## API Response Format
```json
{
    "status": "success|error",
    "message": "Human readable message",
    "data": {
        // Response data
    },
    "errors": [
        // Array of error messages if any
    ]
}
```

This documentation provides the foundation for implementing the Spring API version of the SOC Paperwork Management System. The API maintains the same business logic and security requirements while providing a modern RESTful interface.