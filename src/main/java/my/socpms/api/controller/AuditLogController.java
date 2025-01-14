package my.socpms.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import my.socpms.api.service.AuditService;
import my.socpms.api.dto.ApiResponse;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    
    @Autowired
    private AuditService auditService;

    @GetMapping
    public ResponseEntity<?> getAllLogs() {
        return ResponseEntity.ok(
            new ApiResponse("success", "Audit logs retrieved", auditService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLog(@PathVariable Long id) {
        return ResponseEntity.ok(
            new ApiResponse("success", "Audit log retrieved", auditService.findById(id))
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(
            new ApiResponse("success", "User audit logs retrieved", auditService.findByUserId(userId))
        );
    }

    @GetMapping("/paperwork/{ppwId}")
    public ResponseEntity<?> getPaperworkLogs(@PathVariable Long ppwId) {
        return ResponseEntity.ok(
            new ApiResponse("success", "Paperwork audit logs retrieved", 
                auditService.findByPaperworkId(ppwId))
        );
    }
}