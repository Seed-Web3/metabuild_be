package com.seed.careerhub.endpoint;

import com.seed.careerhub.jpa.BadgeRepository;
import com.seed.careerhub.model.BadgeRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/badge")
public class BadgeEndpoint {
    private final BadgeRepository badgeRepository;

    public BadgeEndpoint(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Operation(summary = "Authenticates a user via signed message with MetaMask")
    @PostMapping("setCategory")
    public ResponseEntity<?> setCategory(@RequestBody BadgeRequest badgeRequest) {
        return ResponseEntity.ok("OK");
    }

}
