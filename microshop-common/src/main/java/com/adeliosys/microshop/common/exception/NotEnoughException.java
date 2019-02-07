package com.adeliosys.microshop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughException extends RuntimeException {

    public NotEnoughException(Class clazz) {
        super("Not enough instances of type '" + clazz.getName() + "' in stock");
    }
}
