package com.midnightsun.noitificationservice.service;

import com.midnightsun.noitificationservice.service.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class NotificationService {

    private final TemplateEngine templateEngine;

    public NotificationService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void sendEmail(OrderDTO order) throws MessagingException, IOException {
        log.debug("Receiving ORDER {} from Order Service", order.getId());
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
//        mimeMessageHelper.setText(getHtmlContent("templates/created-order.html"), true);
        mimeMessageHelper.setText(processedHTML, true);

        mailSender.send(mimeMessage);
    }

    private String getHtmlContent(String htmlFilePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(htmlFilePath)) {
            if (inputStream != null) {
                return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            } else {
                throw new IllegalArgumentException("HTML file not found: " + htmlFilePath);
            }
        }
    }
}
