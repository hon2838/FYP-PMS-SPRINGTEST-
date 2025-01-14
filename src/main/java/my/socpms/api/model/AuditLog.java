package my.socpms.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_audit_log", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id")
})
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;  // Changed to match DB schema (int)

    @Column(length = 50, nullable = false)
    private String action;

    @Column(columnDefinition = "text")
    private String details;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "ppw_id")
    private Integer ppwId;  // Added to track paperwork references
}