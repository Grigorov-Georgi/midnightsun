package com.midnightsun.noitificationservice.service;

import com.midnightsun.noitificationservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public NotificationService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(OrderDTO order) {
        log.debug("Sending email to {} for creation of ORDER {}", order.getCreatedBy(), order.getId());

        String htmlContent = generateHtml(order);

        try {
            mailSender.send(generateMessage(htmlContent));
        } catch (MessagingException e) {
            log.error("Error during sending an email: {}", e);
        }
    }

    private String generateHtml(OrderDTO orderDTO) {
        Context context = new Context();
        context.setVariable("order", orderDTO);
        return templateEngine.process("created-order.html", context);
    }

    private MimeMessage generateMessage(String htmlContent) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom("system@mail.com");
        mimeMessageHelper.setTo("g.grigorov@blubito");
        mimeMessageHelper.setSubject("Order is created");
        mimeMessageHelper.setText(htmlContent, true);

        return mimeMessage;
    }
}
