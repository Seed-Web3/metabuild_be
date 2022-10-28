package com.seed.careerhub.service;

import com.seed.careerhub.domain.MagicLink;
import com.seed.careerhub.jpa.MagicLinkRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class AuthenticationService {
    @Value("${magiclink.expiresInMinute}")
    private Integer MAGIC_LINK_EXPIRES_IN_MINUTE;

    @Value("${magiclink.subject}")
    private String MAGIC_LINK_SUBJECT;

    @Value("${magiclink.host}")
    private String MAGIC_LINK_HOST;

    private final MagicLinkRepository magicLinkRepository;
    private final SimpleEmailService emailService;

    public AuthenticationService(MagicLinkRepository magicLinkRepository, SimpleEmailService emailService) {
        this.magicLinkRepository = magicLinkRepository;
        this.emailService = emailService;
    }

    public void sendMagicLink(String email) throws MessagingException {
        MagicLink link = new MagicLink(MAGIC_LINK_EXPIRES_IN_MINUTE);
        magicLinkRepository.save(link);
        emailService.sendEmail(email, MAGIC_LINK_SUBJECT, getMagicLinkMessage(link.getUuid()));
    }

    private String getMagicLinkMessage(String uuid) {
        return String.format("Dear User, \n<br/>" +
                "This is your magic link to login: %s/magicLink?code=%s <br/>\n" +
                "\n<br/>" +
                " - SEED Career Hub", MAGIC_LINK_HOST, uuid);
    }
}
