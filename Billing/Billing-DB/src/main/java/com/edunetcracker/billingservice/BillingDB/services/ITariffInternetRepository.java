package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.TariffInternet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;

public interface ITariffInternetRepository{} /*extends JpaRepository<TariffInternet, Long> {

    TariffInternet findTariffInternetByName(String name);

    @Transactional
    @Async
    Long deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("update TariffInternet t set   t.internet_cost = :#{#tariffInternet.getInternet_cost()}," +
            "                             t.internet_balance = :#{#tariffInternet.getInternet_balance()}," +
            "                             t.default_internet_cost = :#{#tariffInternet.getDefault_internet_cost()} where t.name = :#{#tariffInternet.getName()} ")
    void updateTariffInternet(@Param("tariffInternet") TariffInternet tariffInternet);

}*/
