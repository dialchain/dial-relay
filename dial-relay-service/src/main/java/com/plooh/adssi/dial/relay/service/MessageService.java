package com.plooh.adssi.dial.relay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plooh.adssi.store.api.StringStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private final Long expSec;

    private final StringStore store;

    public MessageService(StringStore store, @Value("${dial.relay.message.expsec:172800}") Long expSec) {
        this.store = store;
        this.expSec = expSec;
    }

    public Boolean set(final String sender, final String recipient, final String messageId, final String message) {
        store.set(key(sender, recipient, messageId), message, expSec);
        return Boolean.TRUE;
    }

    public String get(final String sender, final String recipient, final String messageId) {
        return store.get(key(sender, recipient, messageId));
    }

    public Map<String, String> get(final String sender, final String recipient, final List<String> messageIds) {
        final Map<String, String> messages = new HashMap<>();
        for (String messageId : messageIds) {
            messages.put(messageId, get(sender, recipient, messageId));
        }
        return messages;
    }


    public Boolean delete(final String sender, final String recipient, final String messageId) {
        return store.delete(key(sender, recipient, messageId));
    }

    private String key(final String sender, final String recipient, final String messageId){
        return sender + "_" + recipient + "_" + messageId;
    }
}