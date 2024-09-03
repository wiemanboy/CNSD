package com.wiemanboy.cnsdbankapplication.data;

import com.wiemanboy.cnsdbankapplication.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerById(UUID id);

    List<Customer> findAll();

    Optional<Customer> removeCustomerById(UUID id);
}
