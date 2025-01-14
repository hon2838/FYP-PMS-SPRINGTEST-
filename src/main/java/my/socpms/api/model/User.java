// src/main/java/my/socpms/api/model/User.java
package my.socpms.api.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "tbl_users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(length = 20, nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(columnDefinition = "text", nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    private String userType = "user";

    @Column(name = "register_time", nullable = false)
    private LocalDateTime registerTime = LocalDateTime.now();

    @Column(nullable = false)
    private boolean active = true;

    @Column(columnDefinition = "longtext")
    private String settings;

    @Column(length = 100)
    private String department;

    @Column(name = "reporting_to")
    private Long reportingTo;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "text")
    private String address;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "reset_token", length = 64)
    private String resetToken;

    @Column(name = "reset_expires")
    private LocalDateTime resetExpires;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tbl_user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"),
        indexes = {
            @Index(name = "idx_user_role", columnList = "user_id,role_id")
        }
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Paperwork> paperworks;


    // Validation groups
    public interface StaffValidation {}
    public interface HODValidation {}
}
