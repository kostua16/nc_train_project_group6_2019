package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.TariffSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;

public interface ITariffSmsRepository{} /*extends JpaRepository<TariffSms, Long> {

    TariffSms findTariffSmsByName(String name);

    @Transactional
    @Async
    Long deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("update TariffSms t set    t.sms_cost = :#{#tariffSms.getSms_cost()}," +
            "                         t.sms_balance = :#{#tariffSms.getSms_balance()}," +
            "                         t.default_sms_cost = :#{#tariffSms.getDefault_sms_cost()} where t.name = :#{#tariffSms.getName()} ")
    void updateTariffSms(@Param("tariffSms") TariffSms tariffSms);

}*/
