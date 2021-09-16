package com.plooh.adssi.dial.relay.config;

import com.plooh.adssi.store.redis.RedisStoreConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "dial.relay.store.name", havingValue = "redis")
@ComponentScan(basePackageClasses = RedisStoreConfig.class)
public class RedisStringStoreConfig {
}