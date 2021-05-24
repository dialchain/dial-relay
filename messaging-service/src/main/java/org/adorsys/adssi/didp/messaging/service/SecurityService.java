package org.adorsys.adssi.didp.messaging.service;

import org.adorsys.adssi.didp.messaging.config.SecurityConfig;
import org.adorsys.adssi.didp.messaging.exceptions.ExpiredPopSignature;
import org.adorsys.adssi.didp.messaging.exceptions.InvalidPopSignature;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SecurityService {

    private final SignatureService signatureService;
    private final SecurityConfig securityConfig;

    public SecurityService(SignatureService signatureService, SecurityConfig securityConfig) {
        this.signatureService = signatureService;
        this.securityConfig = securityConfig;
    }

    public void assertSignature(String providedSignature, String sender, String recipient, String messageId, Instant timestamp, String key) {
        assertSignature(providedSignature, sender, recipient, messageId, timestamp, key, "");
    }

    public void assertSignature(String providedSignature, String sender, String recipient, String messageId, Instant timestamp, String payload, String key) {
        if (!securityConfig.getEnabled()) {
            return;
        }

        if (Instant.now().isAfter(timestamp.plus(securityConfig.getTtl()))) {
            throw new ExpiredPopSignature();
        }

        var expectedSignature = signatureService.signature(sender, recipient, messageId, timestamp.toString(), payload, key);

        if (!expectedSignature.equals(providedSignature)) {
            throw new InvalidPopSignature();
        }
    }
}
