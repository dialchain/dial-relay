package com.plooh.adssi.dial.relay.config;

import com.plooh.adssi.store.api.StringStore;
import com.plooh.adssi.store.map.MapStringStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "dial.relay.store.name", havingValue = "map", matchIfMissing = true)
public class MapStringStoreConfig {

    @Value("${dial.relay.store.maxSize:1000000}")
    private int maxSize;

    @Value("${dial.relay.store.durationInSeconds:172800}")
    private int durationInSeconds;

    @Bean
    StringStore mapStringOperations() {
        log.info("=== USED Store: EXPIRING MAP ===");

        return new MapStringStore(maxSize, durationInSeconds)
            .addAsyncExpirationListener((key, value) -> log.warn("MapStringStore - Removing entry: {}", key));
    }

}