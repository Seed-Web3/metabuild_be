package com.seed.careerhub.util;

import com.seed.careerhub.model.AuthenticationRequest;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;

import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;

public class EthUtil {

    private static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
    private static final String MESSAGE = "Please sign this message for authentication on the Career Portal.\nYour special nonce: ";

    public static boolean verifyAddressFromSignature(AuthenticationRequest authenticationRequest, String nonce) {
        return verifyAddressFromSignature(authenticationRequest.getPublicAddress(), authenticationRequest.getSignature(), nonce);
    }

    public static boolean verifyAddressFromSignature(String address, String signature, String nonce) {
        String message = MESSAGE + nonce;
        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        SignatureData sd =
                new SignatureData(
                        v,
                        (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32),
                        (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;

        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey =
                    Sign.recoverFromSignature(
                            (byte) i,
                            new ECDSASignature(
                                    new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                            msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);

                if (addressRecovered.equalsIgnoreCase(address)) {
                    match = true;
                    break;
                }
            }
        }

        return match;
    }

//    public static boolean isEthAddress(String text) {
//        return hasLength(text)
//                && startsWithIgnoreCase(text, "0x")
//                && !text.contains(".");
//    }
}
