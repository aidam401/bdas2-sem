package com.bdas2.app.exception;

public class DaoException extends RuntimeException{

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
