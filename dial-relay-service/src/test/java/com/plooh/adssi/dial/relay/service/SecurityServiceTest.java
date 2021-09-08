package com.plooh.adssi.dial.relay.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.nimbusds.jose.JWSAlgorithm;
import com.plooh.adssi.dial.relay.config.SecurityConfig;
import com.plooh.adssi.dial.relay.exceptions.ExpiredPopSignature;
import com.plooh.adssi.dial.relay.exceptions.InvalidPopSignature;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SecurityServiceTest {

    @Mock
    private SignatureService signatureService;

    private SecurityConfig securityConfig = new SecurityConfig(Duration.ofDays(30), true, JWSAlgorithm.EdDSA);

    private SecurityService uut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        uut = new SecurityService(signatureService, securityConfig);
    }

    @Test
    void doNothingIfAssertSignatureIsDisabled() {
        securityConfig = new SecurityConfig(Duration.ofDays(30), false, JWSAlgorithm.EdDSA);
        uut = new SecurityService(signatureService, securityConfig);
        uut.assertSignature("providedSignature",  "sender",  "recipient",  "messageId",  null, "key");
        verify(signatureService, times(0)).verifySignature(any(SignatureService.Verification.class), any(String.class), any(String.class));
    }

    @Test
    void assertSignatureThrowsExceptionBecauseSignatureExpired() {
        assertThrows(ExpiredPopSignature.class, () -> uut.assertSignature("providedSignature",  "sender",  "recipient",  "messageId",  Instant.parse("2020-11-30T00:00:00.00Z"), "key"));
        verify(signatureService, times(0)).verifySignature(any(SignatureService.Verification.class), any(String.class), any(String.class));
    }

    @Test
    void assertSignatureThrowsExceptionBecauseSignatureInvalid() {
        when(signatureService.verifySignature(any(SignatureService.Verification.class), any(String.class), any(String.class))).thenReturn(false);
        assertThrows(
            InvalidPopSignature.class, () -> uut.assertSignature("providedSignature",  "sender",  "recipient",  "messageId",  Instant.now(), "key"));
        verify(signatureService, times(1)).verifySignature(any(SignatureService.Verification.class), any(String.class), any(String.class));
    }

    @Test
    void shouldAssertSignature() {
        when(signatureService.verifySignature(any(SignatureService.Verification.class), any(String.class), any(String.class))).thenReturn(true);
        uut.assertSignature("providedSignature",  "sender",  "recipient",  "messageId",  Instant.now(), "key");
        verify(signatureService, times(1)).verifySignature(any(SignatureService.Verification.class), any(String.class), any(String.class));
    }

}
