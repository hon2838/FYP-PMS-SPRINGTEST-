// src/main/java/my/socpms/api/model/Permission.java
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
@Table(name = "tbl_permissions", indexes = {
    @Index(name = "idx_permission_name", columnList = "permission_name")
})
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_name", unique = true, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}