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
@ConditionalOnProperty(name = "dial.relay.store.map.enabled", havingValue = "true")
public class MapStringStoreConfig {

    @Value("${dial.relay.store.map.maxSize}")
    private int maxSize;

    @Value("${dial.relay.store.map.durationInSeconds}")
    private int durationInSeconds;

    @Bean
    StringStore mapStringOperations() {
        log.info("Starting the Expiring Map Store...");
        return new MapStringStore(maxSize, durationInSeconds);
    }

}