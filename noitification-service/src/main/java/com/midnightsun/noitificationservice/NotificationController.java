package com.midnightsun.noitificationservice;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @RequestMapping("/sendemail/")
    public String sendEmail() throws MessagingException, IOException {
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
        mimeMessageHelper.setText(getHtmlContent("mail-templates/created-order.html"), true);

        mailSender.send(mimeMessage);
        return "Email sent!";
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
