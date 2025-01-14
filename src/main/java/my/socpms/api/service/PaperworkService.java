package my.socpms.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import my.socpms.api.exception.InvalidWorkflowException;
import my.socpms.api.model.Paperwork;
import my.socpms.api.repository.PaperworkRepository;


@Service
public class PaperworkService {
    
    @Autowired
    private PaperworkRepository paperworkRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private AuditService auditService;

    private final Counter paperworkSubmissions;
    
    public PaperworkService(MeterRegistry registry) {
        this.paperworkSubmissions = registry.counter("paperwork.submissions");
    }

    @PreAuthorize("hasAnyRole('STAFF', 'HOD', 'DEAN', 'ADMIN')")
    public Paperwork save(Paperwork paperwork) {
        validateWorkflow(paperwork);
        paperworkSubmissions.increment();
        
        paperwork.setSubmissionTime(LocalDateTime.now());
        paperwork.setCurrentStage(Paperwork.Stage.SUBMITTED.name());
        paperwork.setStatus("0");  // Changed to String
        
        Paperwork saved = paperworkRepository.save(paperwork);
        auditService.logAction(saved.getUserIdAsLong(), "CREATE_PAPERWORK", 
            "Created paperwork: " + paperwork.getRefNumber(), saved.getPpw_id());
        return saved;
    }

    @PreAuthorize("hasRole('HOD')")
    public Paperwork approveByHOD(Long id, String note) {
        Paperwork paperwork = findById(id);
        if (!Paperwork.Stage.SUBMITTED.name().equals(paperwork.getCurrentStage())) {
            throw new InvalidWorkflowException("Paperwork must be in SUBMITTED stage for HOD approval");
        }

        paperwork.setHodApproval(true);
        paperwork.setHodNote(note);
        paperwork.setHodApprovalDate(LocalDateTime.now());
        paperwork.setCurrentStage(Paperwork.Stage.DEAN_REVIEW.name());
        
        Paperwork updated = paperworkRepository.save(paperwork);
        notificationService.sendApprovalNotification(updated);
        return updated;
    }

    @PreAuthorize("hasAnyRole('DEAN', 'ADMIN')")
    public Paperwork approveByDean(Long id, String note) {
        Paperwork paperwork = findById(id);
        if (!Paperwork.Stage.DEAN_REVIEW.name().equals(paperwork.getCurrentStage())) {
            throw new InvalidWorkflowException("Paperwork must be in DEAN_REVIEW stage for Dean approval");
        }

        paperwork.setDeanApproval("1");
        paperwork.setDeanNote(note);
        paperwork.setDeanApprovalDate(LocalDateTime.now());
        paperwork.setCurrentStage(Paperwork.Stage.APPROVED.name());
        paperwork.setStatus("1");
        
        Paperwork updated = paperworkRepository.save(paperwork);
        notificationService.sendApprovalNotification(updated);
        return updated;
    }

    @PreAuthorize("hasAnyRole('HOD', 'DEAN', 'ADMIN')")
    public Paperwork reject(Long id, String reason) {
        Paperwork paperwork = findById(id);
        String currentStage = paperwork.getCurrentStage();
        
        if (!Paperwork.Stage.HOD_REVIEW.name().equals(currentStage) && 
            !Paperwork.Stage.DEAN_REVIEW.name().equals(currentStage)) {
            throw new InvalidWorkflowException("Cannot reject paperwork in current stage");
        }

        paperwork.setCurrentStage(Paperwork.Stage.REJECTED.name());
        paperwork.setStatus("0");
        
        if (Paperwork.Stage.HOD_REVIEW.name().equals(currentStage)) {
            paperwork.setHodNote(reason);
            paperwork.setHodApproval(false);
        } else {
            paperwork.setDeanNote(reason);
            paperwork.setDeanApproval("0");
        }

        Paperwork updated = paperworkRepository.save(paperwork);
        notificationService.sendRejectionNotification(updated);
        return updated;
    }

    private void validateWorkflow(Paperwork paperwork) {
        try {
            if (!isValidTransition(paperwork.getCurrentStage())) {
                throw new InvalidWorkflowException("Invalid workflow stage transition");
            }
        } catch (Exception e) {
            throw new InvalidWorkflowException("Error validating workflow: " + e.getMessage());
        }
    }

    private boolean isValidTransition(String stage) {
        if (stage == null) {
            return true; // Allow null for new submissions
        }
        
        try {
            Paperwork.Stage currentStage = Paperwork.Stage.valueOf(stage);
            return switch (currentStage) {
                case SUBMITTED -> true;
                case HOD_REVIEW -> true;
                case DEAN_REVIEW -> true;
                case APPROVED, REJECTED -> false;
            };
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public List<Paperwork> findAll() {
        return paperworkRepository.findAll();
    }

    public Paperwork findById(Long id) {
        return paperworkRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paperwork not found with id: " + id));
    }

    public List<Paperwork> findByDepartment(String department) {
        return paperworkRepository.findByUser_Department(department);
    }

    public Paperwork update(Long id, Paperwork paperwork) {
        Paperwork existing = findById(id);
        if (!existing.isEditable()) {
            throw new RuntimeException("Paperwork cannot be edited in current stage");
        }
        
        existing.setProjectName(paperwork.getProjectName());
        existing.setPpwType(paperwork.getPpwType());
        existing.setSession(paperwork.getSession());
        // Remove background and aim as they don't exist in schema
        existing.setProjectDate(paperwork.getProjectDate());
        
        Paperwork updated = paperworkRepository.save(existing);
        auditService.logAction(updated.getUserIdAsLong(), "UPDATE_PAPERWORK", 
            "Updated paperwork: " + paperwork.getRefNumber(), updated.getPpw_id());
        return updated;
    }

    public Paperwork approve(Long id) {
        Paperwork paperwork = findById(id);
        if (!paperwork.isApprovable()) {
            throw new RuntimeException("Paperwork cannot be approved in current stage");
        }
        
        if (paperwork.getCurrentStage().equals(Paperwork.Stage.HOD_REVIEW.name())) {
            paperwork.setHodApproval(true);
            paperwork.setHodApprovalDate(LocalDateTime.now());
        } else if (paperwork.getCurrentStage().equals(Paperwork.Stage.DEAN_REVIEW.name())) {
            paperwork.setDeanApproval("1");  // Use "1" for approved in varchar column
            paperwork.setDeanApprovalDate(LocalDateTime.now());
        }
        
        paperwork.moveToNextStage();
        Paperwork approved = paperworkRepository.save(paperwork);
        
        notificationService.sendApprovalNotification(approved);
        auditService.logAction(
            approved.getUserIdAsLong(),  // Convert Integer to Long using helper method
            "APPROVE_PAPERWORK",
            "Approved paperwork: " + approved.getRefNumber(),
            approved.getPpw_id()
        );
        
        return approved;
    }

    public void delete(Long id) {
        Paperwork paperwork = findById(id);
        paperworkRepository.delete(paperwork);
        auditService.logAction(
            paperwork.getUserIdAsLong(), // Use getUserIdAsLong() helper method
            "DELETE_PAPERWORK",
            "Deleted paperwork: " + paperwork.getRefNumber(),
            paperwork.getPpw_id()
        );
    }
}
