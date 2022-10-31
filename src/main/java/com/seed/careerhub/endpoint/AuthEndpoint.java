package com.seed.careerhub.endpoint;

import com.seed.careerhub.domain.User;
import com.seed.careerhub.domain.UserNonce;
import com.seed.careerhub.exception.AccessDenied;
import com.seed.careerhub.jpa.UserNonceRepository;
import com.seed.careerhub.jpa.UserRepository;
import com.seed.careerhub.model.AuthenticationRequest;
import com.seed.careerhub.model.AuthenticationResponse;
import com.seed.careerhub.model.MagicLinkRequest;
import com.seed.careerhub.model.NetworkType;
import com.seed.careerhub.service.AuthenticationService;
import com.seed.careerhub.service.MyUserDetailsService;
import com.seed.careerhub.util.EthUtil;
import com.seed.careerhub.util.JwtUtil;
import com.seed.careerhub.util.NearUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/auth")
public class AuthEndpoint {

    Logger logger = LoggerFactory.getLogger(AuthEndpoint.class);

    private final UserRepository userRepository;

    private final UserNonceRepository userNonceRepository;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final AuthenticationService authenticationService;

    private final JwtUtil jwtUtil;

    public AuthEndpoint(UserRepository userRepository, UserNonceRepository userNonceRepository, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userNonceRepository = userNonceRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
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
            userNonceRepository.save(new UserNonce(address, nonce, account));
        }
        return ResponseEntity.ok(nonce);
    }

    @Operation(summary = "Authenticates a user via signed message with MetaMask")
    @PostMapping("near")
    public ResponseEntity<?> authenticateOnNear(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticate(authenticationRequest, NetworkType.NEAR);
    }

    @Operation(summary = "Authenticates a user via signed message with NEAR wallet")
    @PostMapping("eth")
    public ResponseEntity<?> authenticateInEthereum(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticate(authenticationRequest, NetworkType.ETHEREUM);
    }

    private ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest, NetworkType networkType) {
        String publicAddress = authenticationRequest.getPublicAddress();
        boolean verified = false;

        try {
            if (networkType == NetworkType.NEAR) {
                if (NearUtil.verifyAddressFromSignature(authenticationRequest, getNonce(publicAddress))) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(publicAddress, ""));
                    verified = true;
                }
            } else if (networkType == NetworkType.ETHEREUM) {
                if (EthUtil.verifyAddressFromSignature(authenticationRequest, getNonce(publicAddress))) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(publicAddress, ""));
                    verified = true;
                }
            } else {
                logger.error("No supported network");
                throw new AccessDenied("No supported network");
            }
            if (!verified) {
                logger.warn("Signed message verification failed for address: {}", publicAddress);
                throw new AccessDenied("Signed message verification failed");
            }
        } catch (Exception e) {
            logger.error("Unhandled exception. Reason:", e);
            throw new AccessDenied("Error during authentication", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByNearAddress(publicAddress);
        final String jwt = jwtUtil.generateToken(userDetails);

        // Let's clear user's nonce (avoid reusing nonce, forcing re-sign a new message)
        clearNonce(publicAddress);

        User user = userRepository.findByNearAddress(publicAddress);
        if (user == null) {
            user = new User();
            user.setNearAddress(publicAddress);
            userRepository.save(user);
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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
            logger.error("Failed to send magic link. Reason:", e);
            throw new AccessDenied("Failed to send magic link", e);
        }

        return ResponseEntity.ok("OK");
    }


    @Operation(summary = "Use magic link to login")
    @GetMapping("email/magicLink")
    public ResponseEntity<?> useMagicLink(@RequestParam String code) throws MessagingException {
        String email = authenticationService.verifyMagicLink(code);
        final UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @Operation(summary = "Fake JWT generator")
    @GetMapping("jwt")
    public ResponseEntity<?> fakeJwt() {
        final UserDetails userDetails = userDetailsService.loadUserByEmail("h@cker.com");
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
