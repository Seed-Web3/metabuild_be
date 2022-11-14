package com.seed.careerhub.endpoint;

import com.seed.careerhub.domain.User;
import com.seed.careerhub.domain.UserNonce;
import com.seed.careerhub.exception.AccessDenied;
import com.seed.careerhub.jpa.UserNonceRepository;
import com.seed.careerhub.jpa.UserRepository;
import com.seed.careerhub.model.*;
import com.seed.careerhub.service.AuthenticationService;
import com.seed.careerhub.service.MyUserDetailsService;
import com.seed.careerhub.service.UserService;
import com.seed.careerhub.util.EthUtil;
import com.seed.careerhub.util.JwtUtil;
import com.seed.careerhub.util.NearUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthEndpoint {

    private final UserRepository userRepository;

    private final UserNonceRepository userNonceRepository;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final AuthenticationService authenticationService;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    public AuthEndpoint(UserRepository userRepository, UserNonceRepository userNonceRepository, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, AuthenticationService authenticationService, JwtUtil jwtUtil, UserService userService) {
        this.userRepository = userRepository;
        this.userNonceRepository = userNonceRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }


    @Operation(summary = "Generates nonce for a wallet address")
    @GetMapping("nonce")
    public ResponseEntity<?> generateNonceForAddress(@RequestParam String address,
                                                     @RequestParam(required = false) String account) {
        Optional<UserNonce> userNonce = userNonceRepository.findById(address);
        String nonce;
        if (userNonce.isPresent()) {
            nonce = userNonce.get().getNonce();
        } else {
            nonce = UUID.randomUUID().toString();
            userNonceRepository.save(new UserNonce(address, nonce));
        }
        return ResponseEntity.ok(nonce);
    }

    @Operation(summary = "Authenticates a user via signed message with MetaMask")
    @PostMapping("near")
    public ResponseEntity<?> authenticateOnNear(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticate(authenticationRequest, NetworkType.NEAR);
    }

    private ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest, NetworkType networkType) {
        String publicAddress = authenticationRequest.getPublicAddress();

        try {
            if (NearUtil.verifyAddressFromSignature(authenticationRequest, getNonce(authenticationRequest.getAccount()))) {
                // Let's clear user's nonce (avoid reusing nonce, forcing re-sign a new message)
                clearNonce(publicAddress);
                User user = userRepository.findByNearAddress(publicAddress);
                if (user == null) {
                    user = new User();
                    user.setNearAddress(publicAddress);
                    user = userRepository.saveAndFlush(user);
                }
                final String jwt = jwtUtil.generateToken(user);
                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            } else {
                log.warn("Signed message verification failed for address: {}", publicAddress);
                throw new AccessDenied("Signed message verification failed");
            }
        } catch (Exception e) {
            log.error("Unhandled exception. Reason:", e);
            throw new AccessDenied("Error during authentication", e);
        }
    }

    private String getNonce(String address) throws Exception {
        return userNonceRepository.findById(address).orElseThrow(() -> new Exception("Nonce not found for address " + address)).getNonce();
    }

    private void clearNonce(String address) {
        Optional<UserNonce> userNonce = userNonceRepository.findById(address);
        userNonce.ifPresent(userNonceRepository::delete);
    }

    @Operation(summary = "Sends magic link to the target email address")
    @PostMapping("email")
    public ResponseEntity<?> sendMagicLink(@RequestBody MagicLinkRequest magicLinkRequest) throws MessagingException {
        try {
            authenticationService.sendMagicLink(magicLinkRequest.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send magic link. Reason:", e);
            throw new AccessDenied("Failed to send magic link", e);
        }

        return ResponseEntity.ok("OK");
    }


    @Operation(summary = "Use magic link to login")
    @GetMapping("email/magicLink")
    public ResponseEntity<?> useMagicLink(@RequestParam String code) throws MessagingException {
        String email = authenticationService.verifyMagicLink(code);
        User user = userRepository.findOneByEmail(email).orElseGet(() ->
                userService.createUserWithEmail(email));
        final String jwt = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @Operation(summary = "Fake JWT generator")
    @GetMapping("jwt")
    public ResponseEntity<?> fakeJwt() {
        final UserDetails userDetails = userDetailsService.loadUserByEmail("h@cker.com");
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @Operation(summary = "Verify OAuth2 access token via Google")
    @PostMapping("google")
    public ResponseEntity<?> verifyAccessToken(@RequestBody OAuth2Request oauth2Request) throws MessagingException {
        String email = oauth2Request.getEmail();
        if (authenticationService.isOAuth2AccessTokenValid(oauth2Request.getAccessToken(), email)) {
            User user = userRepository.findOneByEmail(email).orElseGet(() ->
                    userService.createUserWithEmail(email));
            final String jwt = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            log.warn("AccessToken is not valid");
            throw new AccessDenied("No supported network");
        }
    }

}
