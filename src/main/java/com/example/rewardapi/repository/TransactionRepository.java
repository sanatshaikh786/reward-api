package com.example.rewardapi.repository;

import org.springframework.stereotype.Repository;
import com.example.rewardapi.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    List<TransactionEntity> findByCustomerIdAndDateBetween(String customerId, LocalDate start, LocalDate end);
    List<TransactionEntity> findByCustomerId(String customerId);
    List<TransactionEntity> findByDateBetween(LocalDate start, LocalDate end);
}
