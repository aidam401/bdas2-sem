package com.bdas2.app.exception;

public class AppRepositoryException extends RuntimeException {
    public AppRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppRepositoryException(Throwable cause) {
        super(cause);
    }
}
