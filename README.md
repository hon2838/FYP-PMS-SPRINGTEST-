# Paperwork Management System (PMS)

A Spring Boot-based RESTful API for managing academic paperwork submissions and approvals through a hierarchical workflow system.

## Features

- User authentication with JWT
- Role-based access control (ADMIN, STAFF, HOD, DEAN)
- Paperwork submission and approval workflow
- File upload/download management
- Email notifications
- Audit logging
- Redis caching
- API documentation with OpenAPI/Swagger

## Tech Stack

- Java 21
- Spring Boot 3.4.1
- Spring Security
- Spring Data JPA
- MariaDB
- Redis
- JWT Authentication
- Maven
- Docker

## API Endpoints

### Authentication
```
POST /api/auth/login              - Login with credentials
POST /api/auth/register          - Register new user
POST /api/auth/reset-password    - Request password reset
POST /api/auth/refresh           - Refresh JWT token
GET  /api/auth/validate          - Validate JWT token
```

### User Management
```
GET    /api/users                - List all users (ADMIN only)
POST   /api/users                - Create new user (ADMIN only)
GET    /api/users/{id}           - Get user details
PUT    /api/users/{id}           - Update user
DELETE /api/users/{id}           - Delete user (ADMIN only)
```

### Paperwork Management
```
GET    /api/paperworks           - List all paperworks
POST   /api/paperworks           - Submit new paperwork
GET    /api/paperworks/{id}      - Get paperwork details
PUT    /api/paperworks/{id}      - Update paperwork
DELETE /api/paperworks/{id}      - Delete paperwork
POST   /api/paperworks/{id}/approve      - Approve paperwork
POST   /api/paperworks/{id}/reject       - Reject paperwork
GET    /api/paperworks/department/{deptId} - Get department paperworks
POST   /api/paperworks/{id}/hod-approve   - HOD approval
POST   /api/paperworks/{id}/dean-approve  - Dean approval
```

### File Management
```
POST   /api/files/upload         - Upload file
GET    /api/files/{fileId}       - Download file
DELETE /api/files/{fileId}       - Delete file (ADMIN only)
```

### Audit Logs
```
GET    /api/audit-logs           - List all audit logs
GET    /api/audit-logs/{id}      - Get specific audit log
GET    /api/audit-logs/user/{userId}      - Get user's audit logs
GET    /api/audit-logs/paperwork/{ppwId}  - Get paperwork audit logs
```

## Getting Started

### Prerequisites
- Java 21
- Maven
- Docker & Docker Compose

### Running with Docker
```bash
# Build and run with Docker Compose
docker-compose up --build
```

### Running Locally
```bash
# Build the project
mvn clean package

# Run the application
java -jar target/api-0.0.1-SNAPSHOT.jar
```

### Configuration

Key application properties (src/main/resources/application.properties):
```properties
# Database
spring.datasource.url=jdbc:mariadb://localhost:3306/soc_pms
spring.datasource.username=myuser
spring.datasource.password=12345678

# JWT
jwt.secret=your-secret-key
jwt.expiration=86400000

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@soc.edu.my
spring.mail.password=your-app-password
```

## Documentation

API documentation is available through Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

## Security

- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control
- Input validation
- Rate limiting
- XSS protection
- CSRF protection

## Monitoring

Health and metrics endpoints:
```
/actuator/health
/actuator/metrics
/actuator/prometheus
```

## Testing

Run tests with:
```bash
mvn test
```

## License

This project is licensed under the MIT License.
```

This README provides a comprehensive overview of the project's features, endpoints, setup instructions, and technical details that developers would need to understand and work with the system.
This README provides a comprehensive overview of the project's features, endpoints, setup instructions, and technical details that developers would need to understand and work with the system.