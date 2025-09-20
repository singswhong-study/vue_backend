package com.study.vue_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message){
        super(message);
    }
}
