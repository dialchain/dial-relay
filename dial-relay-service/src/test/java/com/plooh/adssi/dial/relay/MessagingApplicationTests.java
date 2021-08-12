package com.plooh.adssi.dial.relay;

import com.plooh.adssi.dial.relay.config.MapStoreConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MessagingApplication.class)
@ContextConfiguration(classes = {MapStoreConfig.class})
public class MessagingApplicationTests {
}
