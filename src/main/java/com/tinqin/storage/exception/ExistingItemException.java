package com.tinqin.storage.exception;

public class ExistingItemException extends RuntimeException {

    private static final String MESSAGE = "This item already exists!";

    public ExistingItemException() {
        super(MESSAGE);
    }
}
