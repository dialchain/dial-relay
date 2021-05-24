package org.adorsys.adssi.didp.messaging.service;

import org.adorsys.adssi.didp.messaging.config.SecurityConfig;
import org.adorsys.udf.UDF;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.nio.charset.StandardCharsets;

@Service
public class SignatureService {

    private final SecurityConfig securityConfig;

    public SignatureService(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    public String signature(String sender, String recipient, String messageId, String timestamp, String payload, String key) {
        var canonicalString = String.format("%s,%s,%s,%s,%s", sender, recipient, messageId, timestamp, payload);
        return UDF.contentDigestOfDataString(
                canonicalString.getBytes(StandardCharsets.UTF_8),
                MimeTypeUtils.TEXT_PLAIN_VALUE,
                securityConfig.getBits(),
                securityConfig.getDigest(),
                key
            );
    }
}
