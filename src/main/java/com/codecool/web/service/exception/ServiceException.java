package com.codecool.web.service.exception;

public abstract class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }
}
