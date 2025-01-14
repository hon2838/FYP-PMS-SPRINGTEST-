package my.socpms.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.socpms.api.event.AuditEvent;
import my.socpms.api.model.AuditLog;
import my.socpms.api.repository.AuditLogRepository;

@Service
public class EventStoreService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void store(AuditEvent event) {
        AuditLog log = convertToAuditLog(event);
        auditLogRepository.save(log);
    }
    
    public List<AuditEvent> getEvents(String entityType, Long entityId) {
        List<AuditLog> logs;
        switch (entityType) {
            case "USER":
                logs = auditLogRepository.findByUserId(entityId);
                break;
            case "PAPERWORK":
                logs = auditLogRepository.findByPpwId(entityId);
                break;
            default:
                return new ArrayList<>();
        }
        return logs.stream()
            .map(this::convertToAuditEvent)
            .collect(Collectors.toList());
    }

    private AuditLog convertToAuditLog(AuditEvent event) {
        AuditLog log = new AuditLog();
        log.setUserId(event.getUserId().intValue()); // Convert Long to Integer
        log.setAction(event.getAction());
        log.setDetails(event.getDetails());
        log.setIpAddress(event.getIpAddress());
        log.setTimestamp(event.getTimestamp() != null ? 
                        event.getTimestamp() : 
                        LocalDateTime.now());
        return log;
    }

    private AuditEvent convertToAuditEvent(AuditLog log) {
        AuditEvent event = new AuditEvent();
        event.setId(log.getId());
        event.setUserId(log.getUserId().longValue()); // Convert Integer to Long
        event.setAction(log.getAction());
        event.setDetails(log.getDetails());
        event.setIpAddress(log.getIpAddress());
        event.setTimestamp(log.getTimestamp() != null ? 
                          log.getTimestamp() : 
                          LocalDateTime.now());
        return event;
    }
}