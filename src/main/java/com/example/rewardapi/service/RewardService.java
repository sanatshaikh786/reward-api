
package com.example.rewardapi.service;

import com.example.rewardapi.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;


@Service
public class RewardService {

    private final List<Transaction> transactions = Arrays.asList(
        new Transaction("T1", "C1", LocalDate.of(2025, 4, 15), 120)
        ,new Transaction("T2", "C1", LocalDate.of(2025, 5, 10), 90)
        ,new Transaction("T4", "C1", LocalDate.of(2025, 6, 5), 130)
    );

    public RewardResponse calculateRewards(String customerId, LocalDate start, LocalDate end) {
        List<TransactionDetail> details = new ArrayList<>();
        Map<String, Integer> monthly = new HashMap<>();
        int total = 0;

        for (Transaction tx : transactions) {
            if (!tx.getCustomerId().equals(customerId)) continue;
            if (tx.getDate().isBefore(start) || tx.getDate().isAfter(end)) continue;

            int points = calculatePoints(tx.getAmount());
            total += points;
            String month = tx.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            monthly.put(month, monthly.getOrDefault(month, 0) + points);
            details.add(new TransactionDetail(tx.getId(), tx.getDate(), tx.getAmount(), points));
        }

        return new RewardResponse(customerId, "Customer " + customerId, total, monthly, details);
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
}
