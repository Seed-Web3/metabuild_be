package com.seed.careerhub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Slf4j
@Service
public class SimpleEmailService implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(body);
        message.setTo(to);
        message.setFrom(from);
        mailSender.send(message);
    }
}
