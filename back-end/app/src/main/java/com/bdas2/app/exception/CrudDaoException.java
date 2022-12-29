package com.bdas2.app.exception;

public class CrudDaoException extends RuntimeException {

    public CrudDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
