package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

//TODO

@Service
public interface IAccountRepository {

    @Query("select b from #{#entityName} b where b.isbn = upper(:isbn_value)")
    List<Account> customFindByMSISDN(@Param("isbn_value")  String isbn);

    Page<Account> findByNameIsNotNull(Pageable pageable);
    Page<Account> findByISBNIsNotNull(Pageable pageable);
    Page<Account> findByNameContaining(String name, Pageable pageable);
    Page<Account> findByNameNotContaining(String name, Pageable pageable);
    Page<Account> findByIsbnContaining(String isbn, Pageable pageable);
    Page<Account> findByIsbnNotContaining(String isbn, Pageable pageable);

    Page<Account> findByName(String name, Pageable pageable);
    Page<Account> findByIsbn(String isbn, Pageable pageable);

    @Procedure("setBookName")
    void setName(BigInteger id, String name);

    @Query("select count(b) from #{#entityName} b where b.id is not null")
    Integer countAll();

}
