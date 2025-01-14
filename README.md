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

Collecting workspace information

I'll help expand the 

README.md

 with more detailed information about the Spring Boot application. Here's the additional content:

```markdown
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
- Maven 3.9+
- Docker & Docker Compose
- MariaDB 10.6+
- Redis 7.0+

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

## Project Structure

```
src/
├── main/
│   ├── java/my/socpms/api/
│   │   ├── config/         # Configuration classes
│   │   ├── controller/     # REST controllers
│   │   ├── dto/           # Data Transfer Objects
│   │   ├── model/         # Entity classes
│   │   ├── repository/    # JPA repositories
│   │   ├── security/      # Security configurations
│   │   └── service/       # Business logic
│   └── resources/
│       ├── static/        # Static resources
│       ├── templates/     # Thymeleaf templates
│       └── application.properties
└── test/                  # Test classes
```

## Key Features

### Role-Based Access Control
- ADMIN: Full system access
- DEAN: Paperwork approval and department oversight
- HOD: Department-level paperwork review
- STAFF: Basic paperwork submission

### Workflow States
1. SUBMITTED - Initial state
2. HOD_REVIEW - Under HOD review
3. DEAN_REVIEW - Under Dean review
4. APPROVED - Final approved state
5. REJECTED - Rejected state

### Security Features
- JWT-based authentication
- Password encryption with BCrypt
- Role-based access control
- Input validation
- Rate limiting
- XSS protection
- CSRF protection

### Monitoring

Health and metrics endpoints:
```
/actuator/health
/actuator/metrics
/actuator/prometheus
```

### Documentation

API documentation is available through Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

### Redis Caching
- User data caching
- Paperwork list caching
- Cache invalidation on updates

### Audit Logging
- User actions tracking
- Paperwork state changes
- File operations
- Login attempts

## Testing

Run tests with:
```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify
```

## Development Guidelines

### Code Style
- Follow Google Java Style Guide
- Use meaningful variable and method names
- Document public APIs
- Write unit tests for new features

### Git Workflow
1. Create feature branch from `develop`
2. Make changes and commit
3. Create pull request
4. Review and merge

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Support

For support and questions, please open an issue in the GitHub repository.
```
