package org.hae.tasklogue.service.email;

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
public class TokenEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public TokenEmailService(SpringTemplateEngine templateEngine) {

        this.templateEngine = templateEngine;
    }

    private SpringTemplateEngine templateEngine;

    @Async

    public void sendEmail(String to,
                          String userName,
                          EmailTemplateName emailTemplateName,
                          String confirmationUrl,
                          String activationCode,
                          String subject
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

        Map<String, Object> props = new HashMap<>();
        props.put("Username", userName);
        props.put("confirmationUrl", confirmationUrl);
        props.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(props);


        helper.setFrom("activation@taskLogue.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templatename, context);

        helper.setText(template, true);
        javaMailSender.send(mimeMessage);
    }
}
