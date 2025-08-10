package com.example.rewardapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "transactionss")
public class TransactionEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double amount;

    public TransactionEntity() {}

    public TransactionEntity(String id, String customerId, LocalDate date, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }

    public void setId(String id) { this.id = id; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setAmount(double amount) { this.amount = amount; }
}
