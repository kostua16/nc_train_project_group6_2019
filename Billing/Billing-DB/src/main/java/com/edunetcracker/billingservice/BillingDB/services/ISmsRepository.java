package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
public interface ISmsRepository extends JpaRepository<Sms, Long> {

    Sms findSmsByLogin(String login);

    @Transactional
    @Async
    Long deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("update Sms t set    t.sms_cost = :#{#sms.getSms_cost()}," +
            "                   t.sms_balance = :#{#sms.getSms_balance()}," +
            "                   t.default_sms_cost = :#{#sms.getDefault_sms_cost()} where t.login = :#{#sms.getLogin()} ")
    void updateSms(@Param("sms") Sms sms);

}
