package com.android.mlpj.southerninvestments;

public class DailyCollectionDetails {

    private String id;
    private String name;
    private int amount;
    private int totalAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DailyCollectionDetails(String id, String name, int amount, int totalAmount) {

        this.id = id;
        this.name = name;
        this.amount = amount;
        this.totalAmount = totalAmount;
    }
}
