package com.wiemanboy.cnsdbankapplication.presentation;

import com.wiemanboy.cnsdbankapplication.domain.exception.BankAccountNotFoundException;
import com.wiemanboy.cnsdbankapplication.domain.exception.CustomerNotFoundException;
import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleException(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Customer %s not found", exception.getId()));
    }

    @ExceptionHandler(BankAccountNotFoundException.class)
    public ResponseEntity<String> handleException(BankAccountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Bank account %s not found", exception.getId()));
    }

    @ExceptionHandler(TooLittleCustomersInBankAccountException.class)
    public ResponseEntity<String> handleException(TooLittleCustomersInBankAccountException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(String.format("Bank account needs at least %s customers", exception.getAmount()));
    }
}
