package com.plooh.adssi.didp.messaging.resource;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.plooh.adssi.didp.messaging.service.MessageService;
import com.plooh.adssi.didp.messaging.service.SecurityService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageResource {
    public static final String PROOF_OF_POSSESSION_HEADER = "X-POP";
    public static final String PROOF_OF_POSSESSION_TIMESTAMP_HEADER = "X-POP-TIMESTAMP";

    private final MessageService messageService;
    private final SecurityService securityService;

    public MessageResource(MessageService messageService, SecurityService securityService) {
        this.messageService = messageService;
        this.securityService = securityService;
    }

    @GetMapping("/messages/{sender}/{recipient}/{messageId}")
    public String receive(
            final @PathVariable("sender") String sender,
            final @PathVariable("recipient") String recipient,
            final @PathVariable("messageId") String messageId,
            final @RequestHeader(PROOF_OF_POSSESSION_HEADER) String recipientPopHeader,
            final @RequestHeader(PROOF_OF_POSSESSION_TIMESTAMP_HEADER) Instant popTimestamp) {
        securityService.assertSignature(recipientPopHeader, sender, recipient, messageId, popTimestamp, recipient);
        return messageService.get(sender, recipient, messageId);
    }

    @GetMapping("/messages/{sender}/{recipient}/multiple/{messageIds}")
    public Map<String, String> receive(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageIds") String[] messageIds,
        final @RequestHeader(PROOF_OF_POSSESSION_HEADER) String recipientPopHeaders,
        final @RequestHeader(PROOF_OF_POSSESSION_TIMESTAMP_HEADER) Instant popTimestamp) {
        var signatures = recipientPopHeaders.split(",");
        IntStream.range(0, messageIds.length)
                .forEach(pos -> securityService.assertSignature(signatures[pos], sender, recipient, messageIds[pos], popTimestamp, recipient));
        return messageService.get(sender, recipient, List.of(messageIds));
    }

    @PostMapping("/messages/{sender}/{recipient}/{messageId}")
    public Boolean send(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageId") String messageId,
        final @RequestBody String message,
        final @RequestHeader(PROOF_OF_POSSESSION_HEADER) String senderPopHeader,
        final @RequestHeader(PROOF_OF_POSSESSION_TIMESTAMP_HEADER) Instant popTimestamp) {
        securityService.assertSignature(senderPopHeader, sender, recipient, messageId, popTimestamp, message, sender);
        return messageService.set(sender, recipient, messageId, message);
    }

    @DeleteMapping("/messages/{sender}/{recipient}/{messageId}")
    public Boolean delete(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageId") String messageId,
        final @RequestHeader(PROOF_OF_POSSESSION_HEADER) String recipientPopHeader,
        final @RequestHeader(PROOF_OF_POSSESSION_TIMESTAMP_HEADER) Instant popTimestamp) {
        securityService.assertSignature(recipientPopHeader, sender, recipient, messageId, popTimestamp, recipient);
        return messageService.delete(sender, recipient, messageId);
    }
}

