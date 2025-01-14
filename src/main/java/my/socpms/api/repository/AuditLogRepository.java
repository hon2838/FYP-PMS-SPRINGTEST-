// src/main/java/my/socpms/api/repository/AuditLogRepository.java
package my.socpms.api.repository;

import my.socpms.api.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserId(Long userId);
    List<AuditLog> findByPpwId(Long ppwId);
}