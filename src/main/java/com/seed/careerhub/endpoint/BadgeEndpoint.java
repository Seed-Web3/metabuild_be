package com.seed.careerhub.endpoint;

import com.seed.careerhub.jpa.BadgeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/badge")
public class BadgeEndpoint {
    private final BadgeRepository badgeRepository;

    public BadgeEndpoint(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }


}
