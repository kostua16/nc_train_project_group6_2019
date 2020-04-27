package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.TariffCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Repository
public interface ITariffCallRepository extends JpaRepository<TariffCall, Long> {

    TariffCall findTariffCallByName(String name);

    @Transactional
    @Async
    Long deleteByName(String name);

    @Modifying
    @Transactional
    @Query("update TariffCall t set   t.call_cost = :#{#tariffCall.getCall_cost()}," +
            "                         t.call_balance = :#{#tariffCall.getCall_balance()}," +
            "                         t.default_call_cost = :#{#tariffCall.getDefault_call_cost()} where t.name = :#{#tariffCall.getName()} ")
    int updateTariffCall(@Param("tariffCall") TariffCall tariffCall);

}
