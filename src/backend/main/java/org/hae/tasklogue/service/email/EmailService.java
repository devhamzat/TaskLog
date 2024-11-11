package org.hae.tasklogue.service.email;

import jakarta.mail.MessagingException;
import org.hae.tasklogue.utils.enums.EmailTemplateName;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    default void sendCollaboratorEmail(String to,
                                       String userName,
                                       EmailTemplateName emailTemplateName,
                                       String acceptanceUrl,
                                       String taskId,
                                       String taskTitle,
                                       String InviterUsername,
                                       String subject
    ) throws MessagingException {}
    default void sendActivationCodeEmail(String to,
                                         String userName,
                                         EmailTemplateName emailTemplateName,
                                         String confirmationUrl,
                                         String activationCode,
                                         String subject
    ) throws MessagingException {}
    default void sendPasswordResetEmail(String to,
                                        String userName,
                                        EmailTemplateName emailTemplateName,
                                        String resetPasswordUrl,
                                        String passwordResetCode,
                                        String subject
    ) throws MessagingException {}


}
