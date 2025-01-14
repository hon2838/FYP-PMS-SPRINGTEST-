// src/main/java/my/socpms/api/repository/UserRepository.java
package my.socpms.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import my.socpms.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}