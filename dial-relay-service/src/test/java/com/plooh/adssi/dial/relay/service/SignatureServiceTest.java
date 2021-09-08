package com.plooh.adssi.dial.relay.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.Ed25519Signer;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.plooh.adssi.dial.relay.config.SecurityConfig;
import com.plooh.adssi.twindow.crypto.Common25519Service;
import com.plooh.adssi.twindow.crypto.Ed25519VerificationKey2018Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SignatureServiceTest {

    private SecurityConfig securityConfig = new SecurityConfig(Duration.ofDays(30), false, JWSAlgorithm.EdDSA);

    private SignatureService uut;

    private final OctetKeyPair senderKey = Ed25519VerificationKey2018Service.generateKeyPair("k1");
    private final OctetKeyPair recipientKey = Ed25519VerificationKey2018Service.generateKeyPair("k2");
    private final Common25519Service commonSvc = new Common25519Service(Curve.Ed25519);
    private final String senderPub58 = commonSvc.publicKeyBase58(senderKey);
    private final String recipientPub58 = commonSvc.publicKeyBase58(recipientKey);
    private final String messageId = UUID.randomUUID().toString();
    private final String payload = UUID.randomUUID().toString();

    @BeforeEach
    public void init() {
        uut = new SignatureService(securityConfig);
    }

    @Test
    void shouldVerifySignature() throws Exception {
        var time = Instant.now().toString();
        String providedSignature = signature(senderPub58, recipientPub58, messageId, time, payload, senderKey);
        SignatureService.Verification verification = new SignatureService.Verification(senderPub58, recipientPub58, messageId, time, payload);
        boolean verified = uut.verifySignature(verification,  senderPub58, providedSignature);
        Assertions.assertTrue(verified);
    }

    private String signature(String senderPubKey, String recipientPubKey, String messageId, String timestamp, String payload, OctetKeyPair key) throws Exception {
        var jwsHeader = new JWSHeader(JWSAlgorithm.EdDSA);
        var jwsPayload = new Payload(
            Map.of(
                "senderId", senderPubKey,
                "recipientId", recipientPubKey,
                "messageId", messageId,
                "timestamp", timestamp,
                "payload", payload
            )
        );

        var jwe = new JWSObject(jwsHeader, jwsPayload);
        jwe.sign(new Ed25519Signer(key));
        return jwe.getSignature().toString();
    }

}
