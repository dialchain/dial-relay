package org.adorsys.adssi.didp.messaging.resource;

import java.util.List;
import java.util.Map;

import org.adorsys.adssi.didp.messaging.service.MessageService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageResource {
    private final MessageService messageService;

    public MessageResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/{sender}/{recipient}/{messageId}")
    public String receive(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageId") String messageId) {
        return messageService.get(sender, recipient, messageId);
    }

    @GetMapping("/messages/{sender}/{recipient}/multiple/{messageIds}")
    public Map<String, String> receive(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageIds") String[] messageIds) {
        return messageService.get(sender, recipient, List.of(messageIds));
    }

    @PostMapping("/messages/{sender}/{recipient}/{messageId}")
    public Boolean send(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageId") String messageId,
        final @RequestBody String message) {
        return messageService.set(sender, recipient, messageId, message);
    }

    @DeleteMapping("/messages/{sender}/{recipient}/{messageId}")
    public Boolean delete(
        final @PathVariable("sender") String sender,
        final @PathVariable("recipient") String recipient,
        final @PathVariable("messageId") String messageId) {
        return messageService.delete(sender, recipient, messageId);
    }
}

