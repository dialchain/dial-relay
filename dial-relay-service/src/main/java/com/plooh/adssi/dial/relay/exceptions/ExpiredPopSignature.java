package com.plooh.adssi.dial.relay.exceptions;

import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ExpiredPopSignature extends DialRelayException {

    private final String sender;
    private final String recipient;
    private final String messageId;

    @Override
    public String getMessage() {
        return MessageFormat.format("Expired Pop Signature: Sender [{0}], Recipient [{1}], MessageId [{2}]", sender, recipient, messageId);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }

}
