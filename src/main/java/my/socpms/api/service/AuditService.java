// src/main/java/my/socpms/api/service/AuditService.java
package my.socpms.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import my.socpms.api.model.AuditLog;
import my.socpms.api.repository.AuditLogRepository;

@Service
public class AuditService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private HttpServletRequest request;

    public List<AuditLog> findAll() {
        return auditLogRepository.findAll();
    }

    public AuditLog findById(Long id) {
        return auditLogRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Audit log not found with id: " + id));
    }

    public List<AuditLog> findByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    public List<AuditLog> findByPaperworkId(Long ppwId) {
        return auditLogRepository.findByPpwId(ppwId);
    }

    public void logAction(Long userId, String action, String details, Integer ppwId) {
        AuditLog log = new AuditLog();
        log.setUserId(userId.intValue());  // Convert Long to Integer
        log.setAction(action);
        log.setDetails(details);
        if (ppwId != null) {
            log.setPpwId(ppwId);  // Fixed: Use ppwId directly since it's already Integer
        }
        log.setIpAddress(request.getRemoteAddr());
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public void logAction(Long userId, String action, String details) {
        AuditLog log = new AuditLog();
        log.setUserId(userId.intValue());
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}