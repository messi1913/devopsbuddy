package com.devopsbuddy.exception;

public class StripeException extends RuntimeException{

    public StripeException(Throwable e) {
        super(e);
    }
}
