package com.seed.careerhub.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    String publicAddress;
    String signature;
    String account;
}
