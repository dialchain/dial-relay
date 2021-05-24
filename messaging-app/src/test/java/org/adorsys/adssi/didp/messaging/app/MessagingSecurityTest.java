package org.adorsys.adssi.didp.messaging.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.adorsys.adssi.didp.messaging.resource.MessageResource.PROOF_OF_POSSESSION_HEADER;
import static org.adorsys.adssi.didp.messaging.resource.MessageResource.PROOF_OF_POSSESSION_TIMESTAMP_HEADER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = MessagingApplication.class)
@ContextConfiguration(classes = {MapStoreConfig.class})
@ActiveProfiles("security")
public class MessagingSecurityTest {

    private static final String BASE_PATH = "/messages/";

    @Autowired
    private MockMvc mvc;

    @Test
    void testSendWithOkSignature() throws Exception {
        doGet("1/2/3", "1", Instant.now().toString());
    }

    @Test
    void testSendWithNokSignature() {
    }

    @Test
    void testReceiveWithOkSignature() {
    }

    @Test
    void testReceiveWithNokSignature() {
    }

    @Test
    void testReceiveMultiWithOkSignature() {
    }

    @Test
    void testReceiveMultiWithNokSignature() {
    }

    @Test
    void testDeleteWithOkSignature() {
    }

    @Test
    void testDeleteWithNokSignature() {
    }

    protected ResultActions doGet(String path, String signature, String timestamp) throws Exception {
        return mvc.perform(
                get(BASE_PATH + path)
                        .header(PROOF_OF_POSSESSION_HEADER, signature)
                        .header(PROOF_OF_POSSESSION_TIMESTAMP_HEADER, timestamp)
        ).andExpect(status().isOk());
    }
}
