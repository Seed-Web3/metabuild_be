package com.seed.careerhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserNonce {
    @Id private String address;
    private String nonce;
    private String account;

    public void UserNonce(String address, String nonce) {
        this.address = address;
        this.nonce = nonce;
        this.account = null;
    }
}
