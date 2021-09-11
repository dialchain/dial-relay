package com.plooh.adssi.dial.relay.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPopSignature extends MessagingException {

    @Override
    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }

}
