package com.seed.careerhub.endpoint;

import com.seed.careerhub.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/")
public class LandingPage {
    Logger logger = LoggerFactory.getLogger(LandingPage.class);
    private EmailService emailService;

    public LandingPage(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "A landing page handle")
    @GetMapping
    public ResponseEntity<?> landingPage() {
        return ResponseEntity.ok("Welcome in SEED Cereer Hub backend. <br/><a href='/swagger-ui/index.html'>Swagger</a>");
    }

    @Operation(summary = "A landing page handle")
    @GetMapping("/mail")
    public ResponseEntity<?> sendTestMail(@RequestParam String to,
                                          @RequestParam String subject,
                                          @RequestParam String body) throws MessagingException {
        emailService.sendHtmlEmail(to, subject, body);
        return ResponseEntity.ok("Test emaill sent");
    }
}
