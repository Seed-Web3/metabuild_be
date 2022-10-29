package com.seed.careerhub.service;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws MessagingException, MessagingException;
    void sendHtmlEmail(String to, String subject, String body) throws MessagingException, MessagingException;
}
