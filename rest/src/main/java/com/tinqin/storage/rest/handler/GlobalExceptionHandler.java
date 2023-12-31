package com.tinqin.storage.rest.handler;

import com.tinqin.storage.core.exception.ExistingItemException;
import com.tinqin.storage.core.exception.NoSuchItemException;
import com.tinqin.storage.core.exception.NegativeUpdatedQuantityException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
    }

    @ExceptionHandler(value = ExistingItemException.class)
    public ResponseEntity<String> handleExistingItemException(ExistingItemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NegativeUpdatedQuantityException.class)
    public ResponseEntity<String> handleNegativeUpdatedQuantityException(NegativeUpdatedQuantityException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchItemException.class)
    public ResponseEntity<String> handleNoSuchItemException(NoSuchItemException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

}
