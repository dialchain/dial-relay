package com.plooh.adssi.dial.relay;

import com.plooh.adssi.dial.relay.config.MapStoreConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MessagingApplication.class,
    properties = {
        "dial.relay.messaging.security.signature.ttl=PT1M",
        "dial.relay.messaging.security.signature.enabled=false",
        "dial.relay.messaging.security.signature.algorithm=EdDSA"}
)
@ContextConfiguration(classes = {MapStoreConfig.class})
public class MessagingApplicationTests {
}
