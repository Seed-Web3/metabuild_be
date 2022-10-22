package com.seed.careerhub.util;

import com.seed.careerhub.model.AuthenticationRequest;
import org.bouncycastle.util.encoders.Base64;
import software.pando.crypto.nacl.Crypto;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class NearUtil {

    public static boolean verifyAddressFromSignature(AuthenticationRequest authenticationRequest, String nonce) {
        return verifySignature(authenticationRequest.getPublicAddress(), authenticationRequest.getSignature(), nonce);
    }

    public static boolean verifySignature(String publicKey,
                                          String signature,
                                          String message) {
        PublicKey pKey = Crypto.signingPublicKey(Base64.decode(publicKey));
        byte[] msg = message.getBytes(StandardCharsets.UTF_8);
        byte[] sig = Base64.decode(signature);
        return Crypto.signVerify(pKey, msg, sig);
    }

    public static void main(String[] args) {
        String publicKeyStr = "bv3SBe/mz3Lo9pu6bZsTvE36Hw7ucErvdlSzBKLL3qM=";
        String signatureStr = "pHImkCN8aXJQJw3mrZP/GSJL7Z7hb0ISpWlHMkVFBwdGeAe9BvdIpRoNJHtn1AjuvawpnDaDWSpZIZbrpt8WDA==";
        String msgStr = "Csaba";
        System.out.println(verifySignature(publicKeyStr, signatureStr, msgStr));
    }

}
