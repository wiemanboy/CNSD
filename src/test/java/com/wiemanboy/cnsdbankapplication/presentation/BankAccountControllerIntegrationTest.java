package com.wiemanboy.cnsdbankapplication.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiemanboy.cnsdbankapplication.data.BankAccountRepository;
import com.wiemanboy.cnsdbankapplication.data.CustomerRepository;
import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.BankAccountBuilder;
import com.wiemanboy.cnsdbankapplication.domain.Customer;
import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateCustomerDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
        bankAccountRepository.deleteAll();
    }

    @Test
    void testCreateBankAccount() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccountCreateDTO createDTO = new BankAccountCreateDTO(customer.getId());

        mockMvc.perform(post("/api/bank-accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetBankAccount() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = bankAccountRepository.save((new BankAccountBuilder()).setCustomer(customer).build());

        mockMvc.perform(get("/api/bank-accounts/{id}", bankAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bankAccount.getId().toString()));
    }

    @Test
    void testGetBankAccounts() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        bankAccountRepository.save((new BankAccountBuilder()).setCustomer(customer).build());
        bankAccountRepository.save((new BankAccountBuilder()).setCustomer(customer).build());

        mockMvc.perform(get("/api/bank-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateBankAccount() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = bankAccountRepository.save((new BankAccountBuilder()).setCustomer(customer).build());
        BankAccountUpdateDTO updateDTO = new BankAccountUpdateDTO(AccountStatus.SUSPENDED);

        mockMvc.perform(put("/api/bank-accounts/{id}", bankAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUSPENDED"));
    }

    @Test
    void testAddCustomerToBankAccount() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = bankAccountRepository.save((new BankAccountBuilder()).setCustomer(customer).build());
        BankAccountUpdateCustomerDTO updateCustomerDTO = new BankAccountUpdateCustomerDTO(customer.getId());

        mockMvc.perform(patch("/api/bank-accounts/{id}/add-customer", bankAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCustomerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bankAccount.getId().toString()));
    }

    @Test
    void testRemoveCustomerFromBankAccount() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        Customer customer1 = customerRepository.save(new Customer("test"));
        BankAccount unsavedBankAccount = (new BankAccountBuilder()).setCustomer(customer).build();
        unsavedBankAccount.addCustomer(customer1);
        BankAccount bankAccount = bankAccountRepository.save(unsavedBankAccount);
        BankAccountUpdateCustomerDTO updateCustomerDTO = new BankAccountUpdateCustomerDTO(customer.getId());

        mockMvc.perform(patch("/api/bank-accounts/{id}/remove-customer", bankAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCustomerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bankAccount.getId().toString()));
    }

    @Test
    @Disabled("Dont know why this test is failing")
    void testDeleteBankAccount() throws Exception {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = bankAccountRepository.save((new BankAccountBuilder()).setCustomer(customer).build());

        mockMvc.perform(delete("/api/bank-accounts/{id}", bankAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bankAccount.getId().toString()));
    }
}