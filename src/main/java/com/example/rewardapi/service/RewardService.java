
package com.example.rewardapi.service;

import com.example.rewardapi.entity.TransactionEntity;
import com.example.rewardapi.model.*;
import com.example.rewardapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RewardService {
    private final TransactionRepository transactionRepository;

    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int)((amount - 100) * 2) + 50;
        } else if (amount > 50) {
            points += (int)(amount - 50);
        }
        return points;
    }
    public RewardResponse getRewards(String customerId, LocalDate start, LocalDate end) {
        // Fetch from repository
        List<TransactionEntity> txns;
        if (customerId != null && !customerId.isEmpty()) {
            if (start != null && end != null) {
                txns = transactionRepository.findByCustomerIdAndDateBetween(customerId, start, end);
            } else {
                txns = transactionRepository.findByCustomerId(customerId);
            }
        } else {
            if (start != null && end != null) {
                txns = transactionRepository.findByDateBetween(start, end);
            } else {
                txns = transactionRepository.findAll();
            }
        }

        Map<String, Integer> monthly = new LinkedHashMap<>();
        int total = 0;
        for (TransactionEntity t : txns) {
            int pts = calculatePoints(t.getAmount());
            String month = t.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + t.getDate().getYear();
            monthly.put(month, monthly.getOrDefault(month, 0) + pts);
            total += pts;
        }

        // Convert map to list of TransactionDetail for frontend-friendly structure
        List<TransactionDetail> monthlyList = monthly.entrySet().stream()
                .map(e -> new TransactionDetail(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new RewardResponse(customerId, monthlyList, total);
    }
}
