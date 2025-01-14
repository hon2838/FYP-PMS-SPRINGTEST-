package my.socpms.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import my.socpms.api.model.Paperwork;
import my.socpms.api.model.User;

@Service
@Slf4j
public class NotificationService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendApprovalNotification(Paperwork paperwork) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(paperwork.getUserEmail());
            message.setSubject("Paperwork Approved - " + paperwork.getRefNumber());
            
            String emailContent = String.format("""
                                                Dear %s,
                                                
                                                Your paperwork submission (Ref: %s) has been approved.
                                                
                                                Project: %s
                                                Current Stage: %s
                                                
                                                Please log in to the system to view the details.
                                                
                                                Best regards,
                                                SOC PMS Team""",
                paperwork.getName(),  // Use name instead of getUser().getName()
                paperwork.getRefNumber(),
                paperwork.getProjectName(),
                paperwork.getCurrentStage()
            );
            
            message.setText(emailContent);
            mailSender.send(message);
            
            log.info("Approval notification sent for paperwork: {}", paperwork.getRefNumber());
        } catch (MailException e) {
            log.error("Failed to send approval notification for paperwork: {}", paperwork.getRefNumber(), e);
            sendSystemErrorNotification(
                "Failed to Send Approval Notification",
                "Error sending approval notification for paperwork: " + paperwork.getRefNumber()
            );
        }
    }

    public void sendRejectionNotification(Paperwork paperwork) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(paperwork.getUserEmail());
            message.setSubject("Paperwork Returned - " + paperwork.getRefNumber());
            
            String note = paperwork.getHodNote() != null ? paperwork.getHodNote() : 
                         paperwork.getDeanNote() != null ? paperwork.getDeanNote() : "No comments provided";
            
            String emailContent = String.format("""
                                                Dear %s,
                                                
                                                Your paperwork submission (Ref: %s) requires revision.
                                                
                                                Project: %s
                                                Current Stage: %s
                                                Comments: %s
                                                
                                                Please log in to the system to make the necessary changes.
                                                
                                                Best regards,
                                                SOC PMS Team""",
                paperwork.getName(),  // Use name instead of getUser().getName()
                paperwork.getRefNumber(),
                paperwork.getProjectName(),
                paperwork.getCurrentStage(),
                note
            );
            
            message.setText(emailContent);
            mailSender.send(message);
            
            log.info("Rejection notification sent for paperwork: {}", paperwork.getRefNumber());
        } catch (MailException e) {
            log.error("Failed to send rejection notification for paperwork: {}", paperwork.getRefNumber(), e);
            sendSystemErrorNotification(
                "Failed to Send Rejection Notification",
                "Error sending rejection notification for paperwork: " + paperwork.getRefNumber()
            );
        }
    }

    public void sendPasswordResetNotification(User user, String newPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Password Reset - SOC PMS");
            
            String emailContent = String.format("""
                                                Dear %s,
                                                
                                                Your password has been reset. Here are your new credentials:
                                                
                                                Email: %s
                                                Temporary Password: %s
                                                
                                                Please change your password after logging in.
                                                
                                                Best regards,
                                                SOC PMS Team""",
                user.getName(),
                user.getEmail(),
                newPassword
            );
            
            message.setText(emailContent);
            mailSender.send(message);
            
            log.info("Password reset notification sent to user: {}", user.getEmail());
        } catch (MailException e) {
            log.error("Failed to send password reset notification to user: {}", user.getEmail(), e);
            sendSystemErrorNotification(
                "Failed to Send Password Reset",
                "Error sending password reset notification to user: " + user.getEmail()
            );
        }
    }

    public void sendSystemErrorNotification(String title, String message) {
        try {
            SimpleMailMessage errorMessage = new SimpleMailMessage();
            errorMessage.setTo("admin@soc.edu.my"); // System admin email
            errorMessage.setSubject("System Error - " + title);
            
            String emailContent = String.format("""
                                                System Error Report
                                                
                                                Title: %s
                                                Message: %s
                                                Timestamp: %s
                                                
                                                This is an automated message.""",
                title,
                message,
                java.time.LocalDateTime.now()
            );
            
            errorMessage.setText(emailContent);
            mailSender.send(errorMessage);
            
            log.error("System error notification sent: {}", title);
        } catch (MailException e) {
            log.error("Failed to send system error notification: {}", title, e);
        }
    }
}