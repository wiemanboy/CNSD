package com.wiemanboy.cnsdbankapplication.data;

import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    @Cacheable("bankAccount")
    Optional<BankAccount> findBankAccountById(UUID id);

    @Cacheable("bankAccounts")
    List<BankAccount> findAll();

    @CacheEvict(value = {"bankAccount", "bankAccounts"})
    void removeBankAccountById(UUID id);

    @CacheEvict(value = {"bankAccount", "bankAccounts"})
    BankAccount save(BankAccount bankAccount);
}
