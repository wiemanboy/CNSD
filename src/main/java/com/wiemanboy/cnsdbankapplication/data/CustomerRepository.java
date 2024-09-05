package com.wiemanboy.cnsdbankapplication.data;

import com.wiemanboy.cnsdbankapplication.domain.Customer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Cacheable("customer")
    Optional<Customer> findCustomerById(UUID id);

    @Cacheable("customers")
    List<Customer> findAll();

    @CacheEvict(value = {"customer", "customers"})
    Optional<Customer> removeCustomerById(UUID id);

    @CacheEvict(value = {"customer", "customers"})
    Customer save(Customer customer);
}
