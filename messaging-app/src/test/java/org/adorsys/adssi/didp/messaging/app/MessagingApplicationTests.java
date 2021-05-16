package org.adorsys.adssi.didp.messaging.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = MessagingApplication.class)
@ContextConfiguration(classes = {MapStoreConfig.class})
@ActiveProfiles("test")
public class MessagingApplicationTests {

	@Test
	void contextLoads() {}
}
