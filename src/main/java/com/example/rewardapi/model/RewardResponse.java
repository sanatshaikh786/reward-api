
package com.example.rewardapi.model;

import java.util.List;
import java.util.Map;

public class RewardResponse {
    private String customerId;
    private List<TransactionDetail> monthlyRewards;
    private int totalPoints;


    public RewardResponse(String customerId, List<TransactionDetail> monthlyRewards, int totalPoints) {
        this.customerId = customerId;
        this.monthlyRewards = monthlyRewards;
        this.totalPoints = totalPoints;
    }

    public String getCustomerId() { return customerId; }
    public List<TransactionDetail> getMonthlyRewards() { return monthlyRewards; }
    public int getTotalPoints() { return totalPoints; }

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setMonthlyRewards(List<TransactionDetail> monthlyRewards) { this.monthlyRewards = monthlyRewards; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

}
