package com.wiemanboy.cnsdbankapplication.feature;

import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateCustomerDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.CustomerCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.response.BankAccountDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.response.CustomerDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountStepDefs {
    private final String baseUrl = "http://localhost:8080/";
    private final String bankAccountUrl = baseUrl + "api/bank-accounts";
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private ResponseEntity<BankAccountDTO> bankAccountResponse;
    private ResponseEntity<String> response;

    @Given("a BankAccount with {int} Customers")
    public void aBankAccountWithCustomers(int numberOfCustomers) {
        ResponseEntity<CustomerDTO> customerResponse = restTemplate.postForEntity(
                baseUrl + "api/customers",
                new CustomerCreateDTO("test"),
                CustomerDTO.class
        );

        if (customerResponse.getBody() == null) {
            throw new IllegalStateException("Customer id is null");
        }

        bankAccountResponse = restTemplate.postForEntity(
                bankAccountUrl,
                new BankAccountCreateDTO(customerResponse.getBody().id()),
                BankAccountDTO.class
        );

        if (bankAccountResponse.getBody() == null) {
            throw new IllegalStateException("BankAccount is null");
        }

        for (int i = 1; i < numberOfCustomers; i++) {
            addCustomerToBankAccount(bankAccountResponse.getBody().id());
        }
    }

    @When("a Customer is removed")
    public void theCustomerIsRemoved() {
        if (bankAccountResponse.getBody() == null) {
            throw new IllegalStateException("BankAccount is null");
        }

        response = restTemplate.postForEntity(
                bankAccountUrl + "/{id}/remove-customer",
                new BankAccountUpdateCustomerDTO(bankAccountResponse.getBody().customers().getFirst().id()),
                String.class,
                bankAccountResponse.getBody().id()
        );
    }

    @Then("get error message")
    public void getErrorMessage() {
        assertEquals(400, response.getStatusCode().value());
    }

    @Then("the BankAccount has {int} Customers")
    public void theBankAccountHasCustomers(int expectedCustomerCount) {
        if (bankAccountResponse.getBody() == null) {
            throw new IllegalStateException("BankAccount is null");
        }

        ResponseEntity<BankAccountDTO> response = restTemplate.getForEntity(bankAccountUrl + "/{id}", BankAccountDTO.class, bankAccountResponse.getBody().id());

        if (response.getBody() == null) {
            throw new IllegalStateException("BankAccount is null");
        }

        assertEquals(expectedCustomerCount, response.getBody().customers().size());
    }

    private void addCustomerToBankAccount(UUID bankAccountId) {
        ResponseEntity<CustomerDTO> createdCustomerResponse = restTemplate.postForEntity(
                baseUrl + "api/customers",
                new CustomerCreateDTO("test"),
                CustomerDTO.class
        );

        if (createdCustomerResponse.getBody() == null) {
            throw new IllegalStateException("Customer id is null");
        }

        restTemplate.postForEntity(
                bankAccountUrl + "/{id}/add-customer",
                new BankAccountUpdateCustomerDTO(createdCustomerResponse.getBody().id()),
                String.class,
                bankAccountId
        );
    }
}
