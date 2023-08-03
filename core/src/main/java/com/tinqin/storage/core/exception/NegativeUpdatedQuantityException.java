package com.tinqin.storage.core.exception;

public class NegativeUpdatedQuantityException extends RuntimeException {

    private static final String MESSAGE = "Not enough quantity for %s!";

    public NegativeUpdatedQuantityException(String itemId) {
        super(String.format(MESSAGE, itemId));
    }
}
