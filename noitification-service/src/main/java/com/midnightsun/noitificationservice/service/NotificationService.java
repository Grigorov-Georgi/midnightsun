package com.midnightsun.noitificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Slf4j
@Service
public class NotificationService {

    @Value("${mail.sender}")
    private String sender;

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(UUID orderId, String receiver, String status, String htmlContent) {
        log.debug("Sending email to {} for creation of ORDER {}", receiver, orderId);

        try {
            mailSender.send(generateMessage(orderId, receiver, status, htmlContent));
        } catch (MessagingException e) {
            log.error("Error during the process of sending an email: {}", e.getMessage());
        }
    }

    private MimeMessage generateMessage(UUID orderId, String receiver, String status, String htmlContent) throws MessagingException {

        var mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setSubject(generateSubject(status, orderId));
        mimeMessageHelper.setText(htmlContent, true);

        return mimeMessage;
    }

    private String generateSubject(String status, UUID orderId) {
        String subject;
        switch (status) {
            case "APPROVED":
                subject = String.format("Order [%s] was successfully created", orderId);
                break;
            case "PENDING":
                subject = String.format("Order [%s] is registered", orderId);
                break;
            case "CANCELED":
                subject = String.format("Order [%s] is canceled", orderId);
                break;
            default:
                subject = String.format("Order [%s] information", orderId);
        }

        return subject;
    }
}
