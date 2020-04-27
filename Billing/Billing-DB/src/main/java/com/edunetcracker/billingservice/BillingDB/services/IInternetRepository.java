package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Internet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Repository
public interface IInternetRepository extends JpaRepository<Internet, Long> {

    Internet findInternetByLogin(String login);

    @Transactional
    @Async
    Long deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("update Internet t set   t.internet_cost = :#{#internet.getInternet_cost()}," +
            "                       t.internet_balance = :#{#internet.getInternet_balance()}," +
            "                       t.default_internet_cost = :#{#internet.getDefault_internet_cost()} where t.login = :#{#internet.getLogin()} ")
    void updateInternet(@Param("internet") Internet internet);

}
