package com.midnightsun.noitificationservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send/{receiver}")
    public ResponseEntity<Void> sendMail(@PathVariable("receiver") String receiver) {
        emailService.sendSimpleMessage(receiver, "that works", "surprise!");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
