package com.edunetcracker.billingservice.BillingDB.services;

import com.edunetcracker.billingservice.BillingDB.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public interface IHistoryRepository extends JpaRepository<History, Long> {

    /*History findHistoryById(Long id);
    List<History> findHistoriesByDate(String date);*/

    @Query(value = "SELECT * FROM test_history", nativeQuery = true)
    List<History> getAll();

}
