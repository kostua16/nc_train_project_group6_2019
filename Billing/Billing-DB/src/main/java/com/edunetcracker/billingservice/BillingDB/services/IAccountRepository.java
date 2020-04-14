package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public interface IAccountRepository extends JpaRepository<Account, Long> {

    //@Override
    //List<Account> findAll();
    Account findAccountByLogin(String login);
    //Account findAccountByTelephone(String telephone);

    @Transactional
    @Async
    Long deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("update Account t set t.password = :#{#account.getPassword()}," +
                                "t.name = :#{#account.getName()}," +
                                "t.balance = :#{#account.getBalance()}," +
                                "t.tariff = :#{#account.getTariff()}," +
                                "t.rang = :#{#account.getRang()}  where t.login = :#{#account.getLogin()} ")
    void updateAccount(@Param("account") Account account);

    @Modifying
    @Transactional
    @Query("update Account t set t.balance = :#{#account.getBalance()}  where t.login = :#{#account.getLogin()} ")
    void addAccountBalance(@Param("account") Account account);

}
