package com.seed.careerhub.service;

import com.seed.careerhub.domain.MagicLink;
import com.seed.careerhub.exception.AccessDenied;
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

    @Value("${magiclink.uri}")
    private String MAGIC_LINK_URI;

    private final MagicLinkRepository magicLinkRepository;
    private final EmailService emailService;

    public AuthenticationService(MagicLinkRepository magicLinkRepository, EmailService emailService) {
        this.magicLinkRepository = magicLinkRepository;
        this.emailService = emailService;
    }

    public void sendMagicLink(String email) throws MessagingException {
        MagicLink link = new MagicLink(email, MAGIC_LINK_EXPIRES_IN_MINUTE);
        magicLinkRepository.save(link);
        emailService.sendHtmlEmail(email, MAGIC_LINK_SUBJECT, getMagicLinkMessage(link.getCode()));
    }

    public String verifyMagicLink(String code) throws MessagingException {
        MagicLink link = magicLinkRepository.findOneByCode(code).orElseThrow(() -> new AccessDenied("Wrong code"));
        magicLinkRepository.delete(link);
        return link.getEmail();
    }

    private String getMagicLinkMessage(String uuid) {
        return String.format("Dear User, \n<br/>" +
                "This is your <a href='%s?code=%s'>MAGIC LINK</a> to login<br/>\n" +
                "\n<br/>" +
                " - SEED Career Hub", MAGIC_LINK_URI, uuid);
    }

    // TODO implement it
    public boolean isOAuth2AccessTokenValid(String accessToken, String email) {
        return true;
    }
}
