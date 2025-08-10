
package com.example.rewardapi.model;

public class TransactionDetail {
    private String month;
    private int points;

    public TransactionDetail(String month, int points) {
        this.month = month;
        this.points = points;
    }


    public String getMonth() { return month; }
    public int getPoints() { return points; }
    public void setMonth(String month) { this.month = month; }
    public void setPoints(int points) { this.points = points; }
}
