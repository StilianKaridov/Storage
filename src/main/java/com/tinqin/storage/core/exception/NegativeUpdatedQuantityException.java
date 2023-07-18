package com.tinqin.storage.core.exception;

public class NegativeUpdatedQuantityException extends RuntimeException {

    private static final String MESSAGE = "The updated quantity must be greater than 0!";

    public NegativeUpdatedQuantityException() {
        super(MESSAGE);
    }
}
