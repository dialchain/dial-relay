package org.adorsys.adssi.didp.messaging;

import org.adorsys.adssi.didp.messaging.config.MapStoreConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MessagingApplication.class)
@ContextConfiguration(classes = {MapStoreConfig.class})
public class MessagingApplicationTests {
}
