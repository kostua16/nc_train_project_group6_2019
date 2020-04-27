package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Repository
public interface ICallRepository extends JpaRepository<Call, Long> {

    Call findCallByLogin(String login);

    @Transactional
    @Async
    Long deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("update Call t set   t.call_cost = :#{#call.getCall_cost()}," +
            "                   t.call_balance = :#{#call.getCall_balance()}," +
            "                   t.default_call_cost = :#{#call.getDefault_call_cost()} where t.login = :#{#call.getLogin()} ")
    int updateCall(@Param("call") Call call);

}
