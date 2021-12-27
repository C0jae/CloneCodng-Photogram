package com.cos.photogramstart.handler.ex;

public class CustomAPIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomAPIException(String message) {
        super(message);
    }
}
