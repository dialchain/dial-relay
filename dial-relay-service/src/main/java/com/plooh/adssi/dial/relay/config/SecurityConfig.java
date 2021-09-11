package com.plooh.adssi.dial.relay.config;

import com.nimbusds.jose.JWSAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
@Configuration
@ConfigurationProperties("dial.relay.messaging.security.signature")
public class SecurityConfig {

    @NotNull
    private Duration ttl;

    @NotNull
    private Boolean enabled;

    @NotNull
    private JWSAlgorithm algorithm;
}
