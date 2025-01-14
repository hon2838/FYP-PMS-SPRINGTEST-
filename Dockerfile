FROM eclipse-temurin:21-jre
WORKDIR /app

# Create directory for file uploads
RUN mkdir -p /app/uploads

# Copy jar from local target
COPY target/*.jar app.jar

# Environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV FILE_UPLOAD_DIR=/app/uploads

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]