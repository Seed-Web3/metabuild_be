package com.seed.careerhub.model;

import lombok.Data;

@Data
public class MagicLinkRequest {
    String email;
    String code;
}
