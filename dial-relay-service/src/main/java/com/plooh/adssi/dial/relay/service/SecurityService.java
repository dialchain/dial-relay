package com.plooh.adssi.dial.relay.service;

import com.plooh.adssi.dial.relay.config.SecurityConfig;
import com.plooh.adssi.dial.relay.exceptions.ExpiredPopSignature;
import com.plooh.adssi.dial.relay.exceptions.InvalidPopSignature;
import org.apache.commons.lang3.BooleanUtils;
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
        assertSignature(providedSignature, sender, recipient, messageId, timestamp, "", key);
    }

    public void assertSignature(String providedSignature, String sender, String recipient, String messageId, Instant timestamp, String payload, String key) {
        if (BooleanUtils.isFalse(securityConfig.getEnabled())) {
            return;
        }

        if (timestamp == null || Instant.now().isAfter(timestamp.plus(securityConfig.getTtl()))) {
            throw new ExpiredPopSignature();
        }

        if (!signatureService.verifySignature(new SignatureService.Verification(sender, recipient, messageId, timestamp.toString(), payload), key, providedSignature)) {
            throw new InvalidPopSignature();
        }
    }
}
