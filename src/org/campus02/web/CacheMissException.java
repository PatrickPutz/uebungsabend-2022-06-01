package org.campus02.web;

public class CacheMissException extends Exception{

    public CacheMissException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheMissException(String message) {
        super(message);
    }
}
