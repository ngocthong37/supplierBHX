package com.supplierBHX.repository;

import com.supplierBHX.entity.Account;
import com.supplierBHX.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserName(String userName);
}
