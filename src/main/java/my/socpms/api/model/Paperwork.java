package my.socpms.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "tbl_ppw", indexes = {
    @Index(name = "idx_user_email", columnList = "user_email")
})
@Data
public class Paperwork {
    public enum Stage {
        SUBMITTED("Submitted"),
        HOD_REVIEW("HOD Review"),
        DEAN_REVIEW("Dean Review"),
        APPROVED("Approved"),
        REJECTED("Rejected");

        private final String displayName;

        Stage(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ppw_id")
    private Integer id; // Changed from Long to Integer to match DB schema (int(11))

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "text")
    private String name;

    @Column(nullable = false)
    private String session;

    @NotBlank(message = "Project name is required")
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull(message = "Project date is required")
    @Column(name = "project_date", nullable = false)
    private LocalDate projectDate;

    @Column(name = "submission_time", columnDefinition = "timestamp NULL DEFAULT current_timestamp()")
    private LocalDateTime submissionTime;

    @Column(length = 50)
    private String status;

    @Column(name = "ref_number", nullable = false, length = 50)
    @NotBlank(message = "Reference number is required")
    private String refNumber;

    @Column(name = "ppw_type", nullable = false, length = 50)
    private String ppwType;

    @Column(name = "document_path", length = 255)
    private String documentPath;

    @Column(name = "admin_note", columnDefinition = "text")
    private String adminNote;

    @Column(name = "current_stage", length = 50, columnDefinition = "varchar(50) DEFAULT 'hod_review'")
    private String currentStage = Stage.SUBMITTED.name();

    @Column(name = "hod_approval")
    private Boolean hodApproval;

    @Column(name = "hod_note", columnDefinition = "text")
    private String hodNote;

    @Column(name = "hod_approval_date")
    private LocalDateTime hodApprovalDate;

    @Column(name = "dean_approval", length = 255)
    private String deanApproval;

    @Column(name = "dean_note", columnDefinition = "text")
    private String deanNote;

    @Column(name = "dean_approval_date")
    private LocalDateTime deanApprovalDate;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    // Helper methods for ID access
    public Integer getPpw_id() {
        return id;
    }

    public void setPpw_id(Integer id) {
        this.id = id;
    }

    // Existing methods
    public boolean isEditable() {
        return getCurrentStage().equals(Stage.SUBMITTED.name()) || 
               getCurrentStage().equals(Stage.HOD_REVIEW.name());
    }

    public boolean isApprovable() {
        return getCurrentStage().equals(Stage.HOD_REVIEW.name()) || 
               getCurrentStage().equals(Stage.DEAN_REVIEW.name());
    }

    public boolean isRejectable() {
        return isApprovable();
    }

    public void moveToNextStage() {
        Stage currentStage = Stage.valueOf(getCurrentStage());
        switch (currentStage) {
            case HOD_REVIEW:
                setCurrentStage(Stage.DEAN_REVIEW.name());
                break;
            case DEAN_REVIEW:
                setCurrentStage(Stage.APPROVED.name());
                setStatus("1");  // Approved
                break;
            default:
                break;
        }
    }

    public void reject() {
        setCurrentStage(Stage.REJECTED.name());
        setStatus("0");  // Rejected
    }

    // Helper method for user ID
    public Long getUserIdAsLong() {
        return user != null ? user.getId() : null;
    }
}