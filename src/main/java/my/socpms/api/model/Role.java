// src/main/java/my/socpms/api/model/Role.java
package my.socpms.api.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_roles", indexes = {
    @Index(name = "idx_role_name", columnList = "role_name")
})
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    
    @Column(name = "role_name", unique = true, length = 50)
    private String name;
    
    @Column(columnDefinition = "text")
    private String description;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "tbl_role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"),
        indexes = {
            @Index(name = "idx_role_permission", columnList = "role_id,permission_id")
        }
    )
    private Set<Permission> permissions = new HashSet<>();
}