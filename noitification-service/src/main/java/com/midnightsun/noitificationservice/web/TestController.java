package com.midnightsun.noitificationservice.web;

import com.midnightsun.noitificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/test-send-mail")
public class TestController {

    private final NotificationService notificationService;

    public TestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

//    @GetMapping
//    public ResponseEntity<String> testSendMail() throws IOException {
//        return ResponseEntity.status(HttpStatus.OK).body(notificationService.sendEmail());
//    }
}
