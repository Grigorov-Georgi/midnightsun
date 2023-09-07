package com.midnightsun.noitificationservice.service;

import com.midnightsun.noitificationservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
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

        try {
            mailSender.send(generateMessage(order));
        } catch (MessagingException e) {
            log.error("Error during sending an email: {}", e);
        }
    }

    private MimeMessage generateMessage(OrderDTO order) throws MessagingException {

        var mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom("system@mail.com");

        String receiver = order.getCustomerEmail();
        mimeMessageHelper.setTo(receiver);

        String subject;
        switch (order.getStatus()) {
            case APPROVED:
                subject = String.format("Order [%s] was successfully created", order.getId());
                break;
            case PENDING:
                subject = String.format("Order [%s] is registered", order.getId());
                break;
            case CANCELED:
                subject = String.format("Order [%s] is canceled", order.getId());
                break;
            default:
                subject = String.format("Order [%s] information", order.getId());
        }
        mimeMessageHelper.setSubject(subject);

        String htmlContent = generateHtml(order);
        mimeMessageHelper.setText(htmlContent, true);

        return mimeMessage;
    }

    private String generateHtml(OrderDTO orderDTO) {
        Context context = new Context();
        context.setVariable("order", orderDTO);
        return templateEngine.process("created-order.html", context);
    }
}
