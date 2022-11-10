package com.seed.careerhub.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seed.careerhub.model.AuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.bouncycastle.util.encoders.Base64;
import software.pando.crypto.nacl.Crypto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@Slf4j
public class NearUtil {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String url = "https://rpc.testnet.near.org";


    public static boolean verifyAddressFromSignature(AuthenticationRequest authenticationRequest, String nonce) {
        if (verifySignature(authenticationRequest.getPublicAddress(), authenticationRequest.getSignature(), nonce)) {
            if (verifyAccountAllowedPublicKey(authenticationRequest.getAccount(), authenticationRequest.getPublicAddress())) {
                return true;
            } else {
                log.warn("Login with NEAR failed, user's account key list doesn't contain the used public key");
            }
        } else {
            log.warn("Login with NEAR failed, signature is not valid");
        }
        return false;
    }

    private static boolean verifyAccountAllowedPublicKey(String account, String publicAddress) {
        try {
            Map result = callRpc("view_access_key_list", account);
            List<Map> keys = (List) ((Map) result.get("result")).get("keys");
            for (Map key: keys) {
                if (publicAddress.equals(key.get("public_key"))) {
                    System.out.println("Found, " + publicAddress + " is in access_key: " + key.toString());
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("Failed to verify Account accessKeys contains the pubKey", e);
        }
        return false;
    }

    private static Map callRpc(String method, String account) throws IOException {
        String json = "{\n" +
                      "  \"jsonrpc\": \"2.0\",\n" +
                      "  \"id\": \"dontcare\",\n" +
                      "  \"method\": \"query\",\n" +
                      "  \"params\": {\n" +
                      "    \"request_type\": \"" + method + "\",\n" +
                      "    \"finality\": \"final\",\n" +
                      "    \"account_id\": \"" + account + "\"\n" +
                      "  }\n" +
                      "}";
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String resp = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            Map result = mapper.readValue(resp, Map.class);
            return result;
        }
    }

    public static boolean verifySignature(String publicKey,
                                          String signature,
                                          String message) {
        PublicKey pKey = Crypto.signingPublicKey(Base64.decode(publicKey));
        byte[] msg = message.getBytes(StandardCharsets.UTF_8);
        byte[] sig = Base64.decode(signature);
        return Crypto.signVerify(pKey, msg, sig);
    }

    public static void main(String[] args) throws IOException {
        String publicKeyStr = "bv3SBe/mz3Lo9pu6bZsTvE36Hw7ucErvdlSzBKLL3qM=";
        String signatureStr = "pHImkCN8aXJQJw3mrZP/GSJL7Z7hb0ISpWlHMkVFBwdGeAe9BvdIpRoNJHtn1AjuvawpnDaDWSpZIZbrpt8WDA==";
        String msgStr = "Csaba";
        System.out.println(verifySignature(publicKeyStr, signatureStr, msgStr));

        verifyAccountAllowedPublicKey("sotcsa2.testnet", "ed25519:FAtYguYeoU7fiFi4MgXGHgxTdEFCpoZgtuTPfGaMAytQ");
    }

}
