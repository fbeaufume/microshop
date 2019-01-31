package com.adeliosys.microshop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Class clazz, Object id) {
        super("Object '" + id + "' of type '" + clazz.getName() + "' was not found");
    }
}
