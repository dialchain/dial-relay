package org.adorsys.adssi.didp.messaging.config;

import org.adorsys.udf.DigestAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Validated
@Configuration
@ConfigurationProperties("didp.messaging.security.signature")
public class SecurityConfig {

    @NotNull
    private Duration ttl;

    @NotNull
    private Boolean enabled;

    @NotNull
    private DigestAlgorithm digest;

    @Min(20) // Must be multiple of 20
    private int bits;

    public Duration getTtl() {
        return ttl;
    }

    public void setTtl(Duration ttl) {
        this.ttl = ttl;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public DigestAlgorithm getDigest() {
        return digest;
    }

    public void setDigest(DigestAlgorithm digest) {
        this.digest = digest;
    }

    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }
}
