package aymh.momentum.security.common.service;

import aymh.momentum.security.common.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${application.mail.from:noreply@momentum.com}")
    private String mailFrom;

    @Async
    public void sendEmailWithTemplate(
            String to,
            EmailTemplateName templateName,
            Map<String, Object> variables,
            String subject
    ) throws MessagingException {
        String templatePath = "email/" + templateName.getName();

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(templatePath, context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        helper.setFrom(mailFrom);
        helper.setTo(to);
        helper.setSubject(subject != null ? subject : templateName.getDefaultSubject());
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}