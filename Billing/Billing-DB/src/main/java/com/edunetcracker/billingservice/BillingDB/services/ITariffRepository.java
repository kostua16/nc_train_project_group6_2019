package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Call;
import com.edunetcracker.billingservice.BillingDB.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public interface ITariffRepository extends JpaRepository<Tariff, Long> {

    Tariff findTariffByName(String name);

    @Transactional
    @Async
    Long deleteTariffByName(String name);

    /*@Modifying
    @Transactional
    @Query("update Tariff t set   t.name = :#{#tariff.getName()} where t.login = :#{#tariff.getLogin()} ")
    int updateTariff(@Param("tariff") Tariff tariff);*/
    // update = delete and send

}
