package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.exception;

public class WishException extends RuntimeException {
    public WishException() {
        super();
    }

    public WishException(String message) {
        super(message);
    }

    public WishException(String message, Throwable cause) {
        super(message, cause);
    }

    public WishException(Throwable cause) {
        super(cause);
    }
}

