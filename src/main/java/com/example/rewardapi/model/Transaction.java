
package com.example.rewardapi.model;

import java.time.LocalDate;

public class Transaction {
    private String id;
    private String customerId;
    private LocalDate date;
    private double amount;

    public Transaction(String id, String customerId, LocalDate date, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
}
