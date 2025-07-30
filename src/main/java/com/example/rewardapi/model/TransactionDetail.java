
package com.example.rewardapi.model;

import java.time.LocalDate;

public class TransactionDetail {
    private String id;
    private LocalDate date;
    private double amount;
    private int points;

    public TransactionDetail(String id, LocalDate date, double amount, int points) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.points = points;
    }

    public String getId() { return id; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public int getPoints() { return points; }
}
