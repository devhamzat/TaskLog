package org.hae.tasklogue.service.email;

import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.hae.tasklogue.utils.enums.EmailTemplateName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;

    public EmailServiceImpl(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendActivationCodeEmail(String to,
                                        String userName,
                                        EmailTemplateName emailTemplateName,
                                        String confirmationUrl,
                                        String activationCode,
                                        String subject) throws MessagingException {
        sendEmailTemp(to, emailTemplateName, subject, addProps("Username", userName,
                "confirmationUrl", confirmationUrl,
                "activation_code", activationCode, null, null, null, null)

        );
    }

    @Override
    @Async
    public void sendCollaboratorEmail(String to,
                                      String userName,
                                      EmailTemplateName emailTemplateName,
                                      String acceptanceUrl,
                                      String taskId,
                                      String taskTitle,
                                      String InviterUsername,
                                      String subject) throws MessagingException {
        sendEmailTemp(to, emailTemplateName, subject, addProps(
                "Username", userName,
                "inviteUsername", InviterUsername,
                "taskId", taskId,
                "taskTitle", taskTitle,
                "AcceptanceUrl", acceptanceUrl
        ));
    }

    @Override
    public void sendPasswordResetEmail(String to, String userName, EmailTemplateName emailTemplateName, String resetPasswordUrl, String passwordResetCode, String subject) throws MessagingException {
        sendEmailTemp(to, emailTemplateName, subject, addProps("Username", userName,
                "resetPasswordUrl", resetPasswordUrl,
                "reset_password_code", passwordResetCode, null, null, null, null)

        );
    }

    private void sendEmailTemp(String to,
                               EmailTemplateName emailTemplateName,
                               String subject,
                               Map<String, Object> props
    ) throws MessagingException {

        String templatename;
        if (emailTemplateName == null) {
            templatename = "confirm-email";
        } else {
            templatename = emailTemplateName.name();
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );


        Context context = new Context();
        context.setVariables(props);


        helper.setFrom("activation@taskLogue.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templatename, context);

        helper.setText(template, true);
        javaMailSender.send(mimeMessage);
    }

    private Map<String, Object> addProps(@Nullable String firstPropKey,
                                         @Nullable Object firstPropValue,
                                         @Nullable String secondPropKey,
                                         @Nullable Object secondPropValue,
                                         @Nullable String thirdPropKey,
                                         @Nullable Object thirdPropValue,
                                         @Nullable String fourthPropKey,
                                         @Nullable Object fourthPropValue,
                                         @Nullable String fifthPropKey,
                                         @Nullable Object fifthPropValue) {
        Map<String, Object> props = new HashMap<>();
        props.put(firstPropKey, firstPropValue);
        props.put(secondPropKey, secondPropValue);
        props.put(thirdPropKey, thirdPropValue);
        props.put(fourthPropKey, fourthPropValue);
        props.put(fifthPropKey, fifthPropValue);
        return props;
    }
}
