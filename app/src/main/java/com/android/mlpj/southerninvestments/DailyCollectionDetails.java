package com.android.mlpj.southerninvestments;

import android.content.Context;

import java.util.List;

public class DailyCollectionDetails {

    private String id;
    private String name;
    private String amount;
    private int totalAmount;
    private List<DailyCollectionDetails> mDailyCollectionDetails;
    private SQLLiteHelper sqlLiteHelper;
    private Context mContext;


    public DailyCollectionDetails(String id, String name, String amount, int totalAmount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.totalAmount = totalAmount;
    }


    public DailyCollectionDetails() {


    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
