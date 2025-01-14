package my.socpms.api.event;

import java.time.LocalDateTime;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "tbl_audit_log")
@Data
public class AuditEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(columnDefinition = "text")
    private String details;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}