package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

//TODO

@Service
public interface IAccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByNameIsNotNull(Pageable pageable);
    Page<Account> findByNameContaining(String name, Pageable pageable);
    Page<Account> findByNameNotContaining(String name, Pageable pageable);

    Page<Account> findByName(String name, Pageable pageable);
    Page<Account> findByNumber(Long number, Pageable pageable);

    @Query("select count(b) from #{#entityName} b where b.id is not null")
    Integer countAll();

}
