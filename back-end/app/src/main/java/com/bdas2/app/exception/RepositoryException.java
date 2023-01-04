package com.bdas2.app.exception;

public class RepositoryException extends RuntimeException{
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
