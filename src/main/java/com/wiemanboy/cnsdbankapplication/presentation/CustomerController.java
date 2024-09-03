package com.wiemanboy.cnsdbankapplication.presentation;

import com.wiemanboy.cnsdbankapplication.application.CustomerService;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.CustomerCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.CustomerUpdateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.response.CustomerDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerCreateDTO customerCreateDTO) {
        return CustomerDTO.from(customerService.createCustomer(customerCreateDTO.name()));
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return CustomerDTO.from(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable UUID id) {
        return CustomerDTO.from(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomerById(@PathVariable UUID id, @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        return CustomerDTO.from(customerService.updateCustomerById(id, customerUpdateDTO.name()));
    }

    @DeleteMapping("/{id}")
    public CustomerDTO deleteCustomerById(@PathVariable UUID id) {
        return CustomerDTO.from(customerService.deleteCustomerById(id));
    }
}
