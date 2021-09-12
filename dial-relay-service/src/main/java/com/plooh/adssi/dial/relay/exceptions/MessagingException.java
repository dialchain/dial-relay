package com.plooh.adssi.dial.relay.exceptions;

import org.springframework.http.HttpStatus;

public abstract class MessagingException extends RuntimeException {

    public abstract HttpStatus status();

}