package com.midnightsun.noitificationservice.service;

import com.midnightsun.noitificationservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Slf4j
@Service
public class NotificationService {

    private final TemplateEngine templateEngine;

    public NotificationService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void sendEmail(OrderDTO order) throws MessagingException {
        log.debug("Sending email to {} for creation of ORDER {}", order.getCreatedBy(), order.getId());

        Context context = new Context();
        context.setVariable("order", order);
        context.setVariable("totalPrice", "123");
        String processedHTML = templateEngine.process("created-order.html", context);

        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("127.0.0.1");
        mailSender.setPort(1025);
        mailSender.setUsername("");
        mailSender.setPassword("");

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");

        var mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom("system@mail.com");
        mimeMessageHelper.setTo("g.grigorov@blubito");
        mimeMessageHelper.setSubject("Order is created");
        mimeMessageHelper.setText(processedHTML, true);

        mailSender.send(mimeMessage);
    }
}
