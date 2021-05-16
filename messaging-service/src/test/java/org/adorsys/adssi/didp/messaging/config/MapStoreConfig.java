package org.adorsys.adssi.didp.messaging.config;

import org.adorsys.adssi.store.api.StringStore;
import org.adorsys.adssi.store.map.MapStringStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStoreConfig {
    
    @Bean
    StringStore store(){
        return new MapStringStore();
    }
}