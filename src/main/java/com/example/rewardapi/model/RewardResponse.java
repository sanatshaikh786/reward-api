
package com.example.rewardapi.model;

import java.util.List;
import java.util.Map;

public class RewardResponse {
    private String customerId;
    private String customerName;
    private int totalPoints;
    private Map<String, Integer> monthlyBreakdown;
    private List<TransactionDetail> transactions;

    public RewardResponse(String customerId, String customerName, int totalPoints,
                          Map<String, Integer> monthlyBreakdown, List<TransactionDetail> transactions) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.totalPoints = totalPoints;
        this.monthlyBreakdown = monthlyBreakdown;
        this.transactions = transactions;
    }

    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public int getTotalPoints() { return totalPoints; }
    public Map<String, Integer> getMonthlyBreakdown() { return monthlyBreakdown; }
    public List<TransactionDetail> getTransactions() { return transactions; }
}
