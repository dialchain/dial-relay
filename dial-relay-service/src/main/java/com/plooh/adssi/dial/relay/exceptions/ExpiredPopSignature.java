package com.plooh.adssi.dial.relay.exceptions;

import org.springframework.http.HttpStatus;

public class ExpiredPopSignature extends MessagingException {

    @Override
    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }

}
