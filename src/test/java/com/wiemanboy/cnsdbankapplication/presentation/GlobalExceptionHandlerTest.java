package com.wiemanboy.cnsdbankapplication.presentation;

import com.wiemanboy.cnsdbankapplication.domain.exception.BankAccountNotFoundException;
import com.wiemanboy.cnsdbankapplication.domain.exception.CustomerNotFoundException;
import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    @Description("Test if CustomerNotFoundException returns a NOT_FOUND status")
    void testHandleCustomerNotFoundException() {
        CustomerNotFoundException exception = new CustomerNotFoundException(UUID.randomUUID());

        ResponseEntity<String> response = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Description("Test if BankAccountNotFoundException returns a NOT_FOUND status")
    void testHandleBankAccountNotFoundException() {
        BankAccountNotFoundException exception = new BankAccountNotFoundException(UUID.randomUUID());
        ResponseEntity<String> response = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Description("Test if TooLittleCustomersInBankAccountException returns a BAD_REQUEST status")
    void testHandleTooLittleCustomersInBankAccountException() {
        TooLittleCustomersInBankAccountException exception = new TooLittleCustomersInBankAccountException(2);
        ResponseEntity<String> response = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}