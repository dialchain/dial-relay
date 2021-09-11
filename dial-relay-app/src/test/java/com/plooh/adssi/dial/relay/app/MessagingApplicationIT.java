package com.plooh.adssi.dial.relay.app;

import com.plooh.adssi.dial.relay.config.MapStringStoreConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MessagingApplication.class)
@ContextConfiguration(classes = {MapStringStoreConfig.class})
public class MessagingApplicationIT {

	@Test
	void contextLoads() {}
}
